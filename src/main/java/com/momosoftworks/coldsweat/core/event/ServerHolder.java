package com.momosoftworks.coldsweat.core.event;

import net.minecraft.server.MinecraftServer;

import javax.annotation.Nullable;

/**
 * Tracks the currently running logical server so server-thread code can reach it
 * without a direct reference. Replaces NeoForge's {@code ServerLifecycleHooks#getCurrentServer}.
 */
public final class ServerHolder
{
    @Nullable private static MinecraftServer SERVER = null;

    private ServerHolder() {}

    public static void setServer(@Nullable MinecraftServer server)
    {   SERVER = server;
    }

    @Nullable
    public static MinecraftServer getServer()
    {   return SERVER;
    }
}