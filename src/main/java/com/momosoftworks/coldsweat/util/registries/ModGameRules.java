package com.momosoftworks.coldsweat.util.registries;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;
import com.momosoftworks.coldsweat.ColdSweat;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;

public class ModGameRules
{
    public static Holder.Reference<GameRule<Boolean>> RULE_SLUSH_SOURCE_CONVERSION;

    public static void registerGameRules()
    {
        RULE_SLUSH_SOURCE_CONVERSION = register("slush_source_conversion", false);
    }

    private static Holder.Reference<GameRule<Boolean>> register(String name, boolean defaultValue)
    {
        return Registry.registerForHolder(BuiltInRegistries.GAME_RULE, ColdSweat.createKey(name),
                new GameRule<>(GameRuleCategory.MISC, GameRuleType.BOOL, BoolArgumentType.bool(),
                        GameRuleTypeVisitorHelper::call, Codec.BOOL, value -> value ? 1 : 0, defaultValue, FeatureFlagSet.of()));
    }
}