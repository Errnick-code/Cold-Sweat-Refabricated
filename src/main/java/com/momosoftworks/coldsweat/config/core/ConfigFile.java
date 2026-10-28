package com.momosoftworks.coldsweat.config.core;

import com.momosoftworks.coldsweat.ColdSweat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigFile implements ConfigSpec.LoadedConfig
{
    public enum Type
    {
        COMMON,
        CLIENT,
        SERVER
    }

    private final String modId;
    private final String fileName;
    private final Type type;
    private final Path fullPath;
    private final ConfigSpec spec;
    private CommentedConfig config;

    public ConfigFile(String modId, String fileName, Type type, Path fullPath, ConfigSpec spec)
    {
        this.modId = modId;
        this.fileName = fileName;
        this.type = type;
        this.fullPath = fullPath;
        this.spec = spec;
        this.config = new CommentedConfig();
    }

    public String getModId()
    {
        return this.modId;
    }

    public String getFileName()
    {
        return this.fileName;
    }

    public Type getType()
    {
        return this.type;
    }

    public Path getFullPath()
    {
        return this.fullPath;
    }

    public ConfigSpec getSpec()
    {
        return this.spec;
    }

    @Override
    public CommentedConfig config()
    {
        return this.config;
    }

    public void load() throws IOException
    {
        this.config = Files.exists(this.fullPath)
                       ? TomlCodec.parse(this.fullPath)
                       : new CommentedConfig();
        this.spec.acceptConfig(this);
    }

    @Override
    public void save()
    {
        try
        {
            TomlCodec.write(this.config, this.fullPath);
        }
        catch (IOException e)
        {
            ColdSweat.LOGGER.error("Failed to save config file {}", this.fullPath, e);
        }
    }

    public List<String> path()
    {
        return List.of(this.fileName);
    }
}