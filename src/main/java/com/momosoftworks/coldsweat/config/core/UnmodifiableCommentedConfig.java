package com.momosoftworks.coldsweat.config.core;

import java.util.List;
import java.util.Map;

public interface UnmodifiableCommentedConfig extends UnmodifiableConfig
{
    String getComment(String key);

    Map<String, String> comments();
}