package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.config.core.ConfigFile;
import com.momosoftworks.coldsweat.core.event.Event;

public abstract class ConfigEvent extends Event
{
    private final ConfigFile config;

    protected ConfigEvent(ConfigFile config)
    {
        this.config = config;
    }

    public ConfigFile getConfig()
    {
        return this.config;
    }

    public static class Loading extends ConfigEvent
    {
        public Loading(ConfigFile config)
        {
            super(config);
        }
    }

    public static class Reloading extends ConfigEvent
    {
        public Reloading(ConfigFile config)
        {
            super(config);
        }
    }

    public static class Unloading extends ConfigEvent
    {
        public Unloading(ConfigFile config)
        {
            super(config);
        }
    }
}