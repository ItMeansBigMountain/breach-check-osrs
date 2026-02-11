package com.oyama.dmm;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DeadmanBreachPluginTest
{
    public static void main(String[] args) throws Exception
    {
        ExternalPluginManager.loadBuiltin(DeadmanBreachPlugin.class);
        RuneLite.main(args);
    }
}
