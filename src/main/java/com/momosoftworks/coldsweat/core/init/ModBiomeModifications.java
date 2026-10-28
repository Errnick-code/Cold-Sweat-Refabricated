package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.data.tag.ModBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomeModifications
{
    public static void register()
    {
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Decoration.VEGETAL_DECORATION,
                                      ResourceKey.create(Registries.PLACED_FEATURE, ColdSweat.createKey("soul_stalk")));
        BiomeModifications.addFeature(BiomeSelectors.tag(ModBiomeTags.INCREASED_SOUL_STALK), GenerationStep.Decoration.VEGETAL_DECORATION,
                                      ResourceKey.create(Registries.PLACED_FEATURE, ColdSweat.createKey("soul_stalk_common")));
    }
}
