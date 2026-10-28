package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingIncomingDamageEvent extends Event implements Cancellable
{
    private final LivingEntity entity;
    private final DamageSource source;
    private final float amount;

    public LivingIncomingDamageEvent(LivingEntity entity, DamageSource source, float amount)
    {   this.entity = entity;
        this.source = source;
        this.amount = amount;
    }

    public LivingEntity getEntity()
    {   return entity;
    }

    public DamageSource getSource()
    {   return source;
    }

    public float getAmount()
    {   return amount;
    }
}