package com.momosoftworks.coldsweat.config.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class InMemoryFormat
{
    public static final InMemoryFormat UNIVERSAL = new InMemoryFormat(obj -> true);

    private final Predicate<Object> support;

    private InMemoryFormat(Predicate<Object> support)
    {
        this.support = support;
    }

    public static InMemoryFormat withUniversalSupport()
    {
        return UNIVERSAL;
    }

    public static InMemoryFormat withSupport(Predicate<Object> support)
    {
        return new InMemoryFormat(support);
    }

    public boolean supports(Object value)
    {
        return this.support.test(value);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> createMap()
    {
        return new LinkedHashMap<>();
    }

    public boolean isCommentsSupported()
    {
        return true;
    }
}