package com.momosoftworks.coldsweat.compat.thirst;

import com.thirstwastaken2.purity.WaterPurity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class ThirstCompat
{
    private ThirstCompat() {}

    public static boolean hasPurity(ItemStack stack)
    {   return WaterPurity.isStamped(stack);
    }

    public static int getPurity(ItemStack stack)
    {   return WaterPurity.get(stack);
    }

    public static ItemStack setPurity(ItemStack stack, int purity)
    {   return WaterPurity.set(stack, Math.max(WaterPurity.MIN, Math.min(WaterPurity.MAX, purity)));
    }

    public static ItemStack setPurityFromBlock(ItemStack item, BlockPos pos, Level level)
    {   return WaterPurity.setQuality(item, WaterPurity.sampleAt(level, pos));
    }

    public static boolean canBoil(ItemStack stack)
    {   return hasPurity(stack) && WaterPurity.boils(WaterPurity.quality(stack));
    }

    public static ItemStack boilStep(ItemStack stack)
    {   return WaterPurity.setQuality(stack, WaterPurity.boilStep(WaterPurity.quality(stack)));
    }
}
