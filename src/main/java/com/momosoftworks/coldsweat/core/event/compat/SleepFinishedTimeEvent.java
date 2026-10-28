package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

public class SleepFinishedTimeEvent extends Event
{
    private final LevelAccessor level;
    private final Player player;

    public SleepFinishedTimeEvent(LevelAccessor level, Player player)
    {   this.level = level;
        this.player = player;
    }

    public LevelAccessor getLevel()
    {   return level;
    }

    public Player getEntity()
    {   return player;
    }
}