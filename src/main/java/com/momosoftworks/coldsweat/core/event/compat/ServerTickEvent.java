package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.server.MinecraftServer;

public abstract class ServerTickEvent extends Event
{
    private final MinecraftServer server;

    public ServerTickEvent(MinecraftServer server)
    {   this.server = server;
    }

    public MinecraftServer getServer()
    {   return server;
    }

    public static class Pre extends ServerTickEvent implements Cancellable
    {
        public Pre(MinecraftServer server)
        {   super(server);
        }
    }

    public static class Post extends ServerTickEvent
    {
        public Post(MinecraftServer server)
        {   super(server);
        }
    }
}