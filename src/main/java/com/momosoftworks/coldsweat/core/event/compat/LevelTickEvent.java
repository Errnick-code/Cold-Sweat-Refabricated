package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.level.LevelAccessor;

public abstract class LevelTickEvent extends Event
{
    private final LevelAccessor level;

    public LevelTickEvent(LevelAccessor level)
    {   this.level = level;
    }

    public LevelAccessor getLevel()
    {   return level;
    }

    public static class Pre extends LevelTickEvent implements Cancellable
    {
        public Pre(LevelAccessor level)
        {   super(level);
        }
    }

    public static class Post extends LevelTickEvent
    {
        public Post(LevelAccessor level)
        {   super(level);
        }
    }
}