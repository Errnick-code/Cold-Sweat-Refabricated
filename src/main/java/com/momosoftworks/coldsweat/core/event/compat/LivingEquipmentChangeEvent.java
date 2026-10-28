package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LivingEquipmentChangeEvent extends Event implements Cancellable
{
    private final LivingEntity entity;
    private final ItemStack from;
    private final ItemStack to;

    public LivingEquipmentChangeEvent(LivingEntity entity, ItemStack from, ItemStack to)
    {   this.entity = entity;
        this.from = from;
        this.to = to;
    }

    public LivingEntity getEntity()
    {   return entity;
    }

    public ItemStack getFrom()
    {   return from;
    }

    public ItemStack getTo()
    {   return to;
    }
}