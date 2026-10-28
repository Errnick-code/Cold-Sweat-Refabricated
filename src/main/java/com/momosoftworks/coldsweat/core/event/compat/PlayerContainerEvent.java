package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class PlayerContainerEvent extends Event
{
    private final Player player;
    private final AbstractContainerMenu container;

    public PlayerContainerEvent(Player player, AbstractContainerMenu container)
    {   this.player = player;
        this.container = container;
    }

    public Player getEntity()
    {   return player;
    }

    public AbstractContainerMenu getContainer()
    {   return container;
    }

    public static class Open extends PlayerContainerEvent
    {
        public Open(Player player, AbstractContainerMenu container)
        {   super(player, container);
        }
    }

    public static class Close extends PlayerContainerEvent
    {
        public Close(Player player, AbstractContainerMenu container)
        {   super(player, container);
        }
    }
}
