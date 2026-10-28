package com.momosoftworks.coldsweat.core.event;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.api.event.vanilla.ServerConfigsLoadedEvent;
import com.momosoftworks.coldsweat.config.ModUpdater;
import com.momosoftworks.coldsweat.config.core.ConfigManager;
import net.minecraft.server.MinecraftServer;

public final class ConfigLoader
{
    private ConfigLoader() {}

    public static void loadConfigs(MinecraftServer server)
    {
        try
        {
            ConfigManager.loadAll();
            ModUpdater.updateConfigs();
        }
        catch (Exception e)
        {
            ColdSweat.LOGGER.error("Failed to load Cold Sweat configs", e);
        }
        ModEvents.post(new ServerConfigsLoadedEvent(server));
    }
}