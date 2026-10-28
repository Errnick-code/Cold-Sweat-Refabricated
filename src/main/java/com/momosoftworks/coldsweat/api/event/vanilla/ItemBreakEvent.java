package com.momosoftworks.coldsweat.api.event.vanilla;

import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ItemBreakEvent extends Event
{
    private final ItemStack itemStack;
    private final LivingEntity entity;
    private final Consumer<Item> onBroken;

    public ItemBreakEvent(ItemStack itemStack, @Nullable LivingEntity entity, Consumer<Item> onBroken)
    {
        this.itemStack = itemStack;
        this.entity = entity;
        this.onBroken = onBroken;
    }

    public ItemStack getItemStack()
    {   return itemStack;
    }

    @Nullable
    public LivingEntity getEntity()
    {   return entity;
    }

    public Consumer<Item> getBrokenCallback()
    {   return onBroken;
    }
}
