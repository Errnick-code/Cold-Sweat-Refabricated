package com.momosoftworks.coldsweat.core.event.compat.client;

import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.client.player.LocalPlayer;

public abstract class ClientPlayerNetworkEvent extends Event
{
    private final LocalPlayer player;

    protected ClientPlayerNetworkEvent(LocalPlayer player)
    {   this.player = player;
    }

    public LocalPlayer getPlayer()
    {   return player;
    }

    public static final class LoggingIn extends ClientPlayerNetworkEvent
    {
        public LoggingIn(LocalPlayer player)
        {   super(player);
        }
    }

    public static final class LoggingOut extends ClientPlayerNetworkEvent
    {
        public LoggingOut(LocalPlayer player)
        {   super(player);
        }
    }
}
