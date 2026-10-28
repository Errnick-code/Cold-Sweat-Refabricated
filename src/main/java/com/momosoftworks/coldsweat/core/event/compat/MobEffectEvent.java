package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public abstract class MobEffectEvent extends Event
{
    private final MobEffectInstance effectInstance;
    private final LivingEntity entity;

    public MobEffectEvent(MobEffectInstance effectInstance, LivingEntity entity)
    {   this.effectInstance = effectInstance;
        this.entity = entity;
    }

    public MobEffectInstance getEffectInstance()
    {   return effectInstance;
    }

    public LivingEntity getEntity()
    {   return entity;
    }

    public static class Added extends MobEffectEvent implements Cancellable
    {
        public Added(MobEffectInstance effectInstance, LivingEntity entity)
        {   super(effectInstance, entity);
        }
    }

    public static class Removed extends MobEffectEvent
    {
        public Removed(MobEffectInstance effectInstance, LivingEntity entity)
        {   super(effectInstance, entity);
        }
    }

    public static class Remove extends MobEffectEvent implements Cancellable
    {
        public Remove(MobEffectInstance effectInstance, LivingEntity entity)
        {   super(effectInstance, entity);
        }
    }

    public static class ApplyEffect extends MobEffectEvent
    {
        public ApplyEffect(MobEffectInstance effectInstance, LivingEntity entity)
        {   super(effectInstance, entity);
        }
    }

    public static class RemoveAttributeModifiers extends MobEffectEvent implements Cancellable
    {
        public RemoveAttributeModifiers(MobEffectInstance effectInstance, LivingEntity entity)
        {   super(effectInstance, entity);
        }
    }
}