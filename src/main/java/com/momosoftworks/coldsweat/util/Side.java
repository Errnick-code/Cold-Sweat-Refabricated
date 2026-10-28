package com.momosoftworks.coldsweat.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public final class Side
{
    public static boolean isClient()
    {   return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    public static boolean isServer()
    {   return !isClient();
    }
}