package com.oyama.dmm;

import net.runelite.client.config.*;

@ConfigGroup("deadmanbreach")
public interface DeadmanBreachConfig extends Config
{
    @ConfigItem(
            keyName = "showScheduleOnLogin",
            name = "Show schedule on login",
            description = "Displays today's breach times when logging into a Deadman world",
            position = 0
    )
    default boolean showScheduleOnLogin()
    {
        return true;
    }

    @ConfigItem(
            keyName = "enableCommand",
            name = "Enable !breach command",
            description = "Allows players to type !breach to see time remaining",
            position = 1
    )
    default boolean enableCommand()
    {
        return true;
    }

    @ConfigItem(
            keyName = "showOverlay",
            name = "Show countdown overlay",
            description = "Displays time remaining to next breach on screen",
            position = 2
    )
    default boolean showOverlay()
    {
        return true;
    }

    @ConfigItem(
            keyName = "enableNotification",
            name = "5-minute notification",
            description = "Sends a desktop notification 5 minutes before breach",
            position = 3
    )
    default boolean enableNotification()
    {
        return true;
    }
}
