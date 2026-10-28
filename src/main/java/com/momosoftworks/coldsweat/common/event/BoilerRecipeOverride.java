package com.momosoftworks.coldsweat.common.event;

import com.momosoftworks.coldsweat.ColdSweat;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import com.momosoftworks.coldsweat.core.event.SubscribeEvent;
import com.momosoftworks.coldsweat.util.reflection.ReflectionHelper;
import com.momosoftworks.coldsweat.core.event.compat.PlayerContainerEvent;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
public class BoilerRecipeOverride
{
    @SubscribeEvent
    public static void onCraftingTableOpen(PlayerContainerEvent.Open event)
    {
        if (event.getContainer() instanceof net.minecraft.world.inventory.AbstractCraftingMenu crafting
        && crafting.getInputGridSlots().size() == 9)
        {
            MinecraftServer server = event.getEntity().level().getServer();
            if (server == null) return;
            RecipeHolder boilerRecipe = server.getRecipeManager().byKey(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.RECIPE, Identifier.fromNamespaceAndPath(ColdSweat.MOD_ID, "boiler"))).orElse(null);
            if (boilerRecipe == null) return;
            
            crafting.addSlotListener(new ContainerListener()
            {
                @Override
                public void slotChanged(AbstractContainerMenu sendingContainer, int slotId, ItemStack stack)
                {
                    Slot slot = sendingContainer.getSlot(slotId);

                    if (slot instanceof ResultSlot resultSlot)
                    {
                        if (true)
                        {
                            CraftingContainer craftSlots = getCraftingContainer(resultSlot);
                            if (craftSlots != null)
                            {   slot.set(boilerRecipe.value().assemble(craftSlots.asCraftInput()));
                            }
                        }
                    }
                }

                @Override
                public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue)
                {}
            });
        }
    }

    private static final Field SLOT_CRAFT_CONTAINER = ReflectionHelper.findField(ResultSlot.class, "craftSlots");
    static
    {   SLOT_CRAFT_CONTAINER.setAccessible(true);
    }

    @Nullable
    private static CraftingContainer getCraftingContainer(ResultSlot slot)
    {
        try
        {   return (CraftingContainer) SLOT_CRAFT_CONTAINER.get(slot);
        }
        catch (IllegalAccessException e)
        {   ColdSweat.LOGGER.error("Failed to get crafting container from ResultSlot", e);
            return null;
        }
    }
}
