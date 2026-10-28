package com.momosoftworks.coldsweat.config.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Config implements UnmodifiableConfig
{
    protected final Map<String, Object> map;

    public Config()
    {
        this(new LinkedHashMap<>());
    }

    public Config(Map<String, Object> map)
    {
        this.map = map;
    }

    public static Config of(MapCreator creator, InMemoryFormat format)
    {
        return new Config(creator.createMap());
    }

    public static MapCreator getDefaultMapCreator(boolean concurrent, boolean sorted)
    {
        return LinkedHashMap::new;
    }

    @Override
    public Map<String, Object> valueMap()
    {
        return this.map;
    }

    @Override
    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }

    @Override
    public Object get(List<String> path)
    {
        Object current = this;
        for (String key : path)
        {
            if (!(current instanceof Config config))
            {
                return null;
            }
            current = config.map.get(key);
            if (current == null)
            {
                return null;
            }
        }
        return current;
    }

    public Config set(List<String> path, Object value)
    {
        if (path.isEmpty())
        {
            return this;
        }
        Config current = this;
        for (int i = 0; i < path.size() - 1; i++)
        {
            String key = path.get(i);
            Object child = current.map.get(key);
            Config childConfig;
            if (child instanceof Config existing)
            {   childConfig = existing;
            }
            else
            {   childConfig = current.createSubConfig();
                current.map.put(key, childConfig);
            }
            current = childConfig;
        }
        current.map.put(path.get(path.size() - 1), value);
        return this;
    }

    public Config put(String key, Object value)
    {
        this.map.put(key, value);
        return this;
    }

    public Object remove(String key)
    {
        return this.map.remove(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrElse(List<String> path, Supplier<? extends T> defaultSupplier)
    {
        Object value = get(path);
        return value == null ? defaultSupplier.get() : (T) value;
    }

    public int getIntOrElse(List<String> path, Supplier<? extends Number> defaultSupplier)
    {
        Object value = get(path);
        if (value instanceof Number number)
        {
            return number.intValue();
        }
        if (value instanceof String string)
        {
            try
            {
                return Integer.parseInt(string.trim());
            }
            catch (NumberFormatException ignored) {}
        }
        return defaultSupplier.get().intValue();
    }

    public long getLongOrElse(List<String> path, Supplier<? extends Number> defaultSupplier)
    {
        Object value = get(path);
        if (value instanceof Number number)
        {
            return number.longValue();
        }
        if (value instanceof String string)
        {
            try
            {
                return Long.parseLong(string.trim());
            }
            catch (NumberFormatException ignored) {}
        }
        return defaultSupplier.get().longValue();
    }

    public <T extends Enum<T>> T getEnumOrElse(List<String> path, Class<T> clazz, EnumGetMethod converter, Supplier<? extends T> defaultSupplier)
    {
        Object value = get(path);
        if (value == null)
        {
            return defaultSupplier.get();
        }
        T converted = converter.get(value, clazz);
        return converted == null ? defaultSupplier.get() : converted;
    }

    public Config createSubConfig()
    {
        return new Config();
    }

    public CommentedConfig createSubCommentedConfig()
    {
        return new CommentedConfig();
    }

    public UnmodifiableConfig unmodifiable()
    {
        return new UnmodifiableConfig()
        {
            @Override
            public Map<String, Object> valueMap()
            {
                return map;
            }

            @Override
            public boolean isEmpty()
            {
                return map.isEmpty();
            }

            @Override
            public Object get(List<String> path)
            {
                return Config.this.get(path);
            }
        };
    }

    public List<String> pathOf(Object value)
    {
        List<String> path = new ArrayList<>();
        return path;
    }
}