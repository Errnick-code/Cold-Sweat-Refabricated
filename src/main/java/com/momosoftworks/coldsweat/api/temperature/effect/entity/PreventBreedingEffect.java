package com.momosoftworks.coldsweat.api.temperature.effect.entity;

import com.momosoftworks.coldsweat.api.temperature.effect.TempEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.TempEffectType;
import com.momosoftworks.coldsweat.data.codec.util.IntegerBounds;
import net.minecraft.world.entity.LivingEntity;
import com.momosoftworks.coldsweat.core.event.SubscribeEvent;
import com.momosoftworks.coldsweat.core.event.compat.BabyEntitySpawnEvent;

public class PreventBreedingEffect extends TempEffect
{
    public PreventBreedingEffect(TempEffectType<?> type, IntegerBounds bounds)
    {   super(type, bounds);
    }

    @SubscribeEvent
    public void onEntityBreed(BabyEntitySpawnEvent event)
    {
        if (!(event.getParentA() instanceof net.minecraft.world.entity.LivingEntity a && this.test(a))
        || !(event.getParentB() instanceof net.minecraft.world.entity.LivingEntity b && this.test(b)))
        {   event.setCanceled(true);
        }
    }

    @Override
    public Side getSide()
    {   return Side.SERVER;
    }
}
