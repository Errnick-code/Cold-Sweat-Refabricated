package com.momosoftworks.coldsweat.core.event;

import com.momosoftworks.coldsweat.core.init.ModItems;
import com.momosoftworks.coldsweat.core.init.ModPotions;
import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

public class PotionRecipes
{
    public static void init()
    {
        FabricPotionBrewingBuilder.BUILD.register(builder ->
        {
            builder.addMix(Potions.AWKWARD, ModItems.SOUL_SPROUT.value(), ModPotions.ICE_RESISTANCE.value());
            builder.addMix(ModPotions.ICE_RESISTANCE.value(), Items.REDSTONE, ModPotions.LONG_ICE_RESISTANCE.value());
        });
    }
}
