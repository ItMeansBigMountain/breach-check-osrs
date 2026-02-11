package com.oyama.dmm;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@PluginDescriptor(
        name = "Deadman Breach Timer",
        description = "Shows Deadman breach schedule and countdown",
        tags = {"deadman", "dmm", "breach", "pvp"}
)
public class DeadmanBreachPlugin extends Plugin
{
    @Inject private Client client;
    @Inject private DeadmanBreachConfig config;
    @Inject private OverlayManager overlayManager;
    @Inject private DeadmanBreachOverlay overlay;
    @Inject private Notifier notifier;

    private Instant nextBreachTime;
    private boolean notified;
    private boolean scheduleShownThisLogin;

    private static final ZonedDateTime SEASON_START =
            ZonedDateTime.of(2026, 1, 30, 17, 0, 0, 0, ZoneOffset.UTC);

    private static final ZonedDateTime SEASON_END =
            ZonedDateTime.of(2026, 2, 21, 18, 0, 0, 0, ZoneOffset.UTC);

    private static final List<LocalTime> BREACH_TIMES_UTC = List.of(
            LocalTime.of(2, 0),
            LocalTime.of(10, 0),
            LocalTime.of(19, 0)
    );

    @Provides
    DeadmanBreachConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(DeadmanBreachConfig.class);
    }

    @Override
    protected void startUp()
    {
        if (config.showOverlay())
        {
            overlayManager.add(overlay);
        }
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (!event.getGroup().equals("deadmanbreach"))
            return;

        if (event.getKey().equals("showOverlay"))
        {
            if (config.showOverlay())
                overlayManager.add(overlay);
            else
                overlayManager.remove(overlay);
        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        if (event.getGameState() == GameState.LOGIN_SCREEN)
        {
            scheduleShownThisLogin = false;
            return;
        }

        if (event.getGameState() != GameState.LOGGED_IN)
            return;

        if (!isDeadmanWorld())
            return;

        if (!isWithinSeasonWindow())
            return;

        nextBreachTime = calculateNextBreach();
        notified = false;

        if (!scheduleShownThisLogin && config.showScheduleOnLogin())
        {
            sendScheduleLocalTime();
            scheduleShownThisLogin = true;
        }
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        if (!isWithinSeasonWindow())
            return;

        if (event.getType() == ChatMessageType.GAMEMESSAGE)
        {
            String message = event.getMessage().replaceAll("<[^>]*>", "");

            if (message.startsWith("The next breach will appear in"))
            {
                syncFromSkullMessage(message);
                return;
            }
        }

        if (!config.enableCommand())
            return;

        if (event.getMessage().equalsIgnoreCase("!breach"))
        {
            event.getMessageNode().setRuneLiteFormatMessage("");
            sendTimeRemaining();
        }
    }

    @Subscribe
    public void onGameTick(GameTick tick)
    {
        if (!isWithinSeasonWindow())
            return;

        if (nextBreachTime == null)
            return;

        Duration remaining = Duration.between(Instant.now(), nextBreachTime);

        if (remaining.isNegative())
        {
            nextBreachTime = calculateNextBreach();
            notified = false;
            return;
        }

        if (config.enableNotification() && !notified && remaining.toMinutes() == 5)
        {
            notifier.notify("Deadman breach in 5 minutes!");
            notified = true;
        }
    }

    private boolean isDeadmanWorld()
    {
        return client.getWorldType().contains(WorldType.DEADMAN);
    }

    private boolean isWithinSeasonWindow()
    {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        return now.isAfter(SEASON_START) && now.isBefore(SEASON_END);
    }

    private Instant calculateNextBreach()
    {
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);

        for (LocalTime time : BREACH_TIMES_UTC)
        {
            ZonedDateTime candidate = nowUtc.with(time);
            if (candidate.isAfter(nowUtc))
                return candidate.toInstant();
        }

        return nowUtc.plusDays(1)
                .with(BREACH_TIMES_UTC.get(0))
                .toInstant();
    }

    private void sendScheduleLocalTime()
    {
        ZoneId localZone = ZoneId.systemDefault();
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("h:mm a");

        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "",
                "<col=ff9040>Deadman Breaches Today (Local Time):</col>", null);

        for (LocalTime utcTime : BREACH_TIMES_UTC)
        {
            ZonedDateTime utc = nowUtc.with(utcTime);
            ZonedDateTime local = utc.withZoneSameInstant(localZone);

            client.addChatMessage(ChatMessageType.GAMEMESSAGE, "",
                    " • <col=ef1020>" + local.format(formatter) + "</col>", null);
        }
    }

    private void syncFromSkullMessage(String message)
    {
        try
        {
            int hours = 0;
            int minutes = 0;

            String clean = message
                    .replace("The next breach will appear in", "")
                    .replace(".", "")
                    .trim();

            String[] parts = clean.split(",");

            for (String part : parts)
            {
                part = part.trim();

                if (part.contains("hour"))
                    hours = Integer.parseInt(part.split(" ")[0]);
                else if (part.contains("minute"))
                    minutes = Integer.parseInt(part.split(" ")[0]);
            }

            nextBreachTime = Instant.now()
                    .plus(Duration.ofHours(hours))
                    .plus(Duration.ofMinutes(minutes));

            notified = false;

            log.debug("Breach synced from skull: {}h {}m", hours, minutes);
        }
        catch (Exception e)
        {
            log.warn("Failed to parse skull breach message");
        }
    }

    private void sendTimeRemaining()
    {
        if (nextBreachTime == null)
            return;

        Duration remaining = Duration.between(Instant.now(), nextBreachTime);

        long h = remaining.toHours();
        long m = remaining.minusHours(h).toMinutes();
        long s = remaining.minusHours(h).minusMinutes(m).getSeconds();

        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "",
                String.format("Next breach in: %dh %dm %ds", h, m, s), null);
    }

    public Instant getNextBreachTime()
    {
        return nextBreachTime;
    }
}
