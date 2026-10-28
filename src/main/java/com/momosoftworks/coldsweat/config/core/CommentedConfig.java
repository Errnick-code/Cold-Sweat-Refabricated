package com.momosoftworks.coldsweat.config.core;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommentedConfig extends Config implements UnmodifiableCommentedConfig
{
    private final Map<String, String> commentMap;

    public CommentedConfig()
    {
        super(new LinkedHashMap<>());
        this.commentMap = new LinkedHashMap<>();
    }

    public CommentedConfig(Map<String, Object> map, Map<String, String> commentMap)
    {
        super(map);
        this.commentMap = commentMap == null ? new LinkedHashMap<>() : commentMap;
    }

    @Override
    public String getComment(String key)
    {
        return this.commentMap.get(key);
    }

    @Override
    public Map<String, String> comments()
    {
        return this.commentMap;
    }

    public CommentedConfig setComment(String key, String comment)
    {
        if (comment == null)
        {
            this.commentMap.remove(key);
        }
        else
        {
            this.commentMap.put(key, comment);
        }
        return this;
    }

    @Override
    public CommentedConfig createSubConfig()
    {
        return new CommentedConfig();
    }

    public CommentedConfig createSubCommentedConfig()
    {
        return new CommentedConfig();
    }

    @Override
    public UnmodifiableCommentedConfig unmodifiable()
    {
        return new UnmodifiableCommentedConfig()
        {
            @Override
            public String getComment(String key)
            {
                return commentMap.get(key);
            }

            @Override
            public Map<String, String> comments()
            {
                return java.util.Collections.unmodifiableMap(commentMap);
            }

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
            public Object get(java.util.List<String> path)
            {
                return CommentedConfig.this.get(path);
            }
        };
    }
}