package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WandererTradesEvent extends Event
{
    private final List<ItemStack> rareTrades = new ArrayList<>();

    public List<ItemStack> getRareTrades()
    {   return rareTrades;
    }
}