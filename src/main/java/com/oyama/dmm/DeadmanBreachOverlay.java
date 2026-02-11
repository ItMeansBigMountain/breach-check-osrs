package com.oyama.dmm;

import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;

public class DeadmanBreachOverlay extends OverlayPanel
{
    private final DeadmanBreachPlugin plugin;

    @Inject
    private DeadmanBreachOverlay(DeadmanBreachPlugin plugin)
    {
        this.plugin = plugin;
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Instant next = plugin.getNextBreachTime();
        if (next == null)
        {
            return null;
        }

        Duration remaining = Duration.between(Instant.now(), next);
        if (remaining.isNegative())
        {
            return null;
        }

        long hours = remaining.toHours();
        long minutes = remaining.minusHours(hours).toMinutes();
        long seconds = remaining.minusHours(hours).minusMinutes(minutes).getSeconds();

        panelComponent.getChildren().clear();
        panelComponent.getChildren().add(LineComponent.builder()
                .left("Next Breach")
                .right(String.format("%02dh %02dm %02ds", hours, minutes, seconds))
                .build());

        return super.render(graphics);
    }
}
