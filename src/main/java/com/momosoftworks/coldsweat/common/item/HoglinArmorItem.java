package com.momosoftworks.coldsweat.common.item;

import com.momosoftworks.coldsweat.core.init.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public class HoglinArmorItem extends ArmorItemBase
{
    public HoglinArmorItem(ArmorMaterial material, ArmorType type, Properties properties)
    {   super(material, type, properties);
    }

    public boolean makesPiglinsNeutral(ItemStack stack)
    {   return stack.is(ModItems.HOGLIN_HELMET.value());
    }
}