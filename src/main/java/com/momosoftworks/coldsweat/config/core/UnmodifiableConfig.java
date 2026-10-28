package com.momosoftworks.coldsweat.config.core;

import java.util.List;
import java.util.Map;

public interface UnmodifiableConfig
{
    Map<String, Object> valueMap();

    boolean isEmpty();

    Object get(List<String> path);
}