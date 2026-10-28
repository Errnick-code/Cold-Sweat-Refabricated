package com.momosoftworks.coldsweat.config.core;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.ConfigEvent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ConfigManager
{
    private static final Map<String, ConfigFile> CONFIGS = new LinkedHashMap<>();

    private ConfigManager() {}

    public static ConfigFile register(String modId, String relativePath, ConfigFile.Type type, ConfigSpec spec, Path configDir)
    {
        Path fullPath = configDir.resolve(relativePath);
        ConfigFile file = new ConfigFile(modId, relativePath, type, fullPath, spec);
        CONFIGS.put(relativePath, file);
        spec.validateSpec(file);
        return file;
    }

    public static ConfigFile get(String relativePath)
    {
        return CONFIGS.get(relativePath);
    }

    public static List<ConfigFile> all()
    {
        return new ArrayList<>(CONFIGS.values());
    }

    public static void loadAll()
    {
        for (ConfigFile file : CONFIGS.values())
        {
            load(file);
        }
    }

    public static void load(ConfigFile file)
    {
        try
        {
            file.load();
        }
        catch (IOException e)
        {
            ColdSweat.LOGGER.error("Failed to load config file {}", file.getFullPath(), e);
        }
        ModEvents.post(new ConfigEvent.Loading(file));
    }

    public static void reloadAll()
    {
        for (ConfigFile file : CONFIGS.values())
        {
            reload(file);
        }
    }

    public static void reload(ConfigFile file)
    {
        try
        {
            file.load();
        }
        catch (IOException e)
        {
            ColdSweat.LOGGER.error("Failed to reload config file {}", file.getFullPath(), e);
        }
        file.getSpec().afterReload();
        ModEvents.post(new ConfigEvent.Reloading(file));
    }

    public static void saveAll()
    {
        for (ConfigFile file : CONFIGS.values())
        {
            file.save();
        }
    }
}