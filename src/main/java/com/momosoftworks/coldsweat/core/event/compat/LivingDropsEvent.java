package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.Collection;

public class LivingDropsEvent extends Event implements Cancellable
{
    private final LivingEntity entity;
    private final Collection<ItemEntity> drops;

    public LivingDropsEvent(LivingEntity entity, Collection<ItemEntity> drops)
    {   this.entity = entity;
        this.drops = drops;
    }

    public LivingEntity getEntity()
    {   return entity;
    }

    public Collection<ItemEntity> getDrops()
    {   return drops;
    }
}