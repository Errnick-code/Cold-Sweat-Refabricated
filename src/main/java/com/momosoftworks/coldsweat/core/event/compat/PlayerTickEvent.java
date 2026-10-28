package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.player.Player;

public abstract class PlayerTickEvent extends Event
{
    private final Player player;

    public PlayerTickEvent(Player player)
    {   this.player = player;
    }

    public Player getEntity()
    {   return player;
    }

    public static class Pre extends PlayerTickEvent implements Cancellable
    {
        public Pre(Player player)
        {   super(player);
        }
    }

    public static class Post extends PlayerTickEvent
    {
        public Post(Player player)
        {   super(player);
        }
    }
}