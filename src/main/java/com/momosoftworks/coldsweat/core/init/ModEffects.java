package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.common.effect.FrigidnessEffect;
import com.momosoftworks.coldsweat.common.effect.GraceEffect;
import com.momosoftworks.coldsweat.common.effect.IceResistanceEffect;
import com.momosoftworks.coldsweat.common.effect.WarmthEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class ModEffects
{
    public static final Ref<Holder<MobEffect>> FRIGIDNESS = effect("frigidness", new FrigidnessEffect());
    public static final Ref<Holder<MobEffect>> WARMTH = effect("warmth", new WarmthEffect());
    public static final Ref<Holder<MobEffect>> GRACE = effect("grace", new GraceEffect());
    public static final Ref<Holder<MobEffect>> ICE_RESISTANCE = effect("ice_resistance", new IceResistanceEffect());

    private static Ref<Holder<MobEffect>> effect(String name, MobEffect effect)
    {   return new Ref<>(Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ColdSweat.createKey(name), effect));
    }
}
