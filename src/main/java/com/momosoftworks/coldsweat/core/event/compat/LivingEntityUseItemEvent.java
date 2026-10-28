package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class LivingEntityUseItemEvent extends Event
{
    private final LivingEntity entity;
    private final ItemStack stack;
    private final int duration;

    public LivingEntityUseItemEvent(LivingEntity entity, ItemStack stack, int duration)
    {   this.entity = entity;
        this.stack = stack;
        this.duration = duration;
    }

    public LivingEntity getEntity()
    {   return entity;
    }

    public ItemStack getItem()
    {   return stack;
    }

    public int getDuration()
    {   return duration;
    }

    public static class Start extends LivingEntityUseItemEvent
    {
        public Start(LivingEntity entity, ItemStack stack, int duration)
        {   super(entity, stack, duration);
        }
    }

    public static class Tick extends LivingEntityUseItemEvent
    {
        public Tick(LivingEntity entity, ItemStack stack, int duration)
        {   super(entity, stack, duration);
        }
    }

    public static class Finish extends LivingEntityUseItemEvent
    {
        public Finish(LivingEntity entity, ItemStack stack, int duration)
        {   super(entity, stack, duration);
        }
    }

    public static class Release extends LivingEntityUseItemEvent
    {
        public Release(LivingEntity entity, ItemStack stack, int duration)
        {   super(entity, stack, duration);
        }
    }

    public static class Stop extends LivingEntityUseItemEvent implements Cancellable
    {
        public Stop(LivingEntity entity, ItemStack stack, int duration)
        {   super(entity, stack, duration);
        }
    }
}