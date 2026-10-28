package com.momosoftworks.coldsweat.compat.trinkets;

import eu.pb4.trinkets.api.TrinketAttachment;
import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class TrinketsCompat
{
    private TrinketsCompat() {}

    private static TrinketAttachment getAttachment(LivingEntity entity)
    {
        try
        {   return TrinketsApi.getAttachment(entity);
        }
        catch (Throwable e)
        {   return null;
        }
    }

    public static boolean hasEquipped(LivingEntity entity, Predicate<ItemStack> predicate)
    {
        TrinketAttachment attachment = getAttachment(entity);
        return attachment != null && attachment.isEquipped(predicate);
    }

    public static List<ItemStack> getAllEquipped(LivingEntity entity)
    {
        List<ItemStack> stacks = new ArrayList<>();
        TrinketAttachment attachment = getAttachment(entity);
        if (attachment != null)
        {
            attachment.forEach((slot, stack) ->
            {
                if (!stack.isEmpty())
                {   stacks.add(stack);
                }
            });
        }
        return stacks;
    }
}
