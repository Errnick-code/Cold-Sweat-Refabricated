package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.SimpleParticleType;

public class ModParticleTypes
{
    public static final Ref<SimpleParticleType> WARM_AIR = particle("warm_air");
    public static final Ref<SimpleParticleType> COLD_AIR = particle("cold_air");
    public static final Ref<SimpleParticleType> SMOKESTACK_WARM = particle("smokestack_warm");
    public static final Ref<SimpleParticleType> SMOKESTACK_COLD = particle("smokestack_cold");
    public static final Ref<SimpleParticleType> GROUND_MIST = particle("ground_mist");
    public static final Ref<SimpleParticleType> MOB_COLD = particle("mob_cold");
    public static final Ref<SimpleParticleType> MOB_HOT = particle("mob_hot");

    private static Ref<SimpleParticleType> particle(String name)
    {   return new Ref<>(Registry.register(BuiltInRegistries.PARTICLE_TYPE, ColdSweat.createKey(name), net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(true)));
    }
}
