package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.common.fluid.SlushFluid;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;

public class ModFluids
{
    public static final Ref<FlowingFluid> SLUSH = fluid("slush", new SlushFluid.Source());
    public static final Ref<FlowingFluid> FLOWING_SLUSH = fluid("flowing_slush", new SlushFluid.Flowing());

    private static Ref<FlowingFluid> fluid(String name, FlowingFluid fluid)
    {   return new Ref<>(Registry.register(BuiltInRegistries.FLUID, ColdSweat.createKey(name), fluid));
    }
}