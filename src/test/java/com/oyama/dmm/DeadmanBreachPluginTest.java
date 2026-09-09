package com.oyama.dmm;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import net.runelite.client.plugins.PluginDescriptor;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DeadmanBreachPluginTest
{
    @Test
    public void descriptorMatchesPluginHubMetadata()
    {
        PluginDescriptor descriptor = DeadmanBreachPlugin.class.getAnnotation(PluginDescriptor.class);
        assertEquals("Deadman Breach Timer", descriptor.name());
        assertEquals("Shows Deadman breach schedule and countdown", descriptor.description());
        assertArrayEquals(new String[] {"deadman", "dmm", "breach", "pvp"}, descriptor.tags());
    }

    public static void main(String[] args) throws Exception
    {
        ExternalPluginManager.loadBuiltin(DeadmanBreachPlugin.class);
        RuneLite.main(args);
    }
}
