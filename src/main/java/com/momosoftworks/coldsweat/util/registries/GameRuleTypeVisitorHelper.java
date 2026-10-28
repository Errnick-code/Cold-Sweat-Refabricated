package com.momosoftworks.coldsweat.util.registries;

import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;

public final class GameRuleTypeVisitorHelper
{
    private GameRuleTypeVisitorHelper() {}

    public static void call(GameRuleTypeVisitor visitor, GameRule<Boolean> rule)
    {   visitor.visitBoolean(rule);
    }
}