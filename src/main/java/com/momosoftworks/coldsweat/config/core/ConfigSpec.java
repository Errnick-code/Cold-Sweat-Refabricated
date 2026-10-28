package com.momosoftworks.coldsweat.config.core;

import java.util.List;

public interface ConfigSpec
{
    enum CorrectionAction
    {
        ADD,
        REPLACE,
        REMOVE
    }

    interface CorrectionListener
    {
        void onCorrect(CorrectionAction action, List<String> path, Object oldValue, Object newValue);
    }

    interface LoadedConfig
    {
        CommentedConfig config();

        void save();
    }

    void validateSpec(ConfigFile config);

    boolean isLoaded();

    void acceptConfig(LoadedConfig config);

    void afterReload();

    UnmodifiableConfig getSpec();

    UnmodifiableConfig getValues();
}