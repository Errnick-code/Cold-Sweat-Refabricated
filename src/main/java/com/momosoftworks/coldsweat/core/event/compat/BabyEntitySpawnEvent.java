package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.Entity;

public class BabyEntitySpawnEvent extends Event implements Cancellable
{
    private final Entity parentA;
    private final Entity parentB;

    public BabyEntitySpawnEvent(Entity parentA, Entity parentB)
    {   this.parentA = parentA;
        this.parentB = parentB;
    }

    public Entity getParentA()
    {   return parentA;
    }

    public Entity getParentB()
    {   return parentB;
    }
}