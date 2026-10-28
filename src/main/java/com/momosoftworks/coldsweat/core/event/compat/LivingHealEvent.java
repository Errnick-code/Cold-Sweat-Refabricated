package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.LivingEntity;

public class LivingHealEvent extends Event implements Cancellable
{
    private final LivingEntity entity;
    private float amount;

    public LivingHealEvent(LivingEntity entity, float amount)
    {   this.entity = entity;
        this.amount = amount;
    }

    public LivingEntity getEntity()
    {   return entity;
    }

    public float getAmount()
    {   return amount;
    }

    public void setAmount(float amount)
    {   this.amount = amount;
    }
}