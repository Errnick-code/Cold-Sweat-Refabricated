package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.LivingEntity;

public class LivingKnockBackEvent extends Event implements Cancellable
{
    private final LivingEntity entity;
    private double strength;

    public LivingKnockBackEvent(LivingEntity entity, double strength)
    {   this.entity = entity;
        this.strength = strength;
    }

    public LivingEntity getEntity()
    {   return entity;
    }

    public double getStrength()
    {   return strength;
    }

    public void setStrength(double strength)
    {   this.strength = strength;
    }
}