package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.server.MinecraftServer;

public class ServerStartingEvent extends Event implements Cancellable
{
    private final MinecraftServer server;

    public ServerStartingEvent(MinecraftServer server)
    {   this.server = server;
    }

    public MinecraftServer getServer()
    {   return server;
    }
}