package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.Entity;

public abstract class EntityTickEvent extends Event
{
    private final Entity entity;

    public EntityTickEvent(Entity entity)
    {   this.entity = entity;
    }

    public Entity getEntity()
    {   return entity;
    }

    public static class Pre extends EntityTickEvent implements Cancellable
    {
        public Pre(Entity entity)
        {   super(entity);
        }
    }

    public static class Post extends EntityTickEvent
    {
        public Post(Entity entity)
        {   super(entity);
        }
    }
}