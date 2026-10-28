package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.common.world.feature.SoulStalkFeature;
import com.momosoftworks.coldsweat.common.world.feature.SoulStalkFeatureConfig;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;

public class ModFeatures
{
    public static final Ref<Feature<SoulStalkFeatureConfig>> SOUL_STALK_FEATURE =
            new Ref<>(Registry.register(BuiltInRegistries.FEATURE, ColdSweat.createKey("soul_stalk"),
                                        new SoulStalkFeature(SoulStalkFeatureConfig.CODEC)));
}
