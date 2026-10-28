package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.Holder;
import net.minecraft.world.item.alchemy.Potion;

public class ModPotions
{
    public static final Ref<Holder<Potion>> ICE_RESISTANCE = potion("ice_resistance", new Potion("ice_resistance", new MobEffectInstance(ModEffects.ICE_RESISTANCE.value(), 3600)));
    public static final Ref<Holder<Potion>> LONG_ICE_RESISTANCE = potion("long_ice_resistance", new Potion("ice_resistance", new MobEffectInstance(ModEffects.ICE_RESISTANCE.value(), 7200)));

    private static Ref<Holder<Potion>> potion(String name, Potion potion)
    {   return new Ref<>(Registry.registerForHolder(BuiltInRegistries.POTION, ColdSweat.createKey(name), potion));
    }
}
