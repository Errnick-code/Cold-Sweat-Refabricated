package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;

public abstract class EntityEvent extends Event
{
    private final Entity entity;

    public EntityEvent(Entity entity)
    {   this.entity = entity;
    }

    public Entity getEntity()
    {   return entity;
    }

    public static class Size extends EntityEvent implements Cancellable
    {
        private final EntityDimensions oldSize;
        private EntityDimensions newSize;

        public Size(Entity entity, EntityDimensions oldSize, EntityDimensions newSize)
        {   super(entity);
            this.oldSize = oldSize;
            this.newSize = newSize;
        }

        public EntityDimensions getOldSize()
        {   return oldSize;
        }

        public EntityDimensions getNewSize()
        {   return newSize;
        }

        public void setNewSize(EntityDimensions newSize)
        {   this.newSize = newSize;
        }
    }
}