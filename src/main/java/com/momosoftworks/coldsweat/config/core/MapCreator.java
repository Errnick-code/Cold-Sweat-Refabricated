package com.momosoftworks.coldsweat.config.core;

import java.util.LinkedHashMap;
import java.util.Map;

@FunctionalInterface
public interface MapCreator
{
    Map<String, Object> createMap();
}