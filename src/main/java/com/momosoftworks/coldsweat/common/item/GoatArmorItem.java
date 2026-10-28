package com.momosoftworks.coldsweat.common.item;

import com.momosoftworks.coldsweat.core.init.ModItems;
import com.momosoftworks.coldsweat.util.math.CSMath;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public class GoatArmorItem extends ArmorItemBase
{
    public GoatArmorItem(ArmorMaterial material, ArmorType type, Properties properties)
    {   super(material, type, properties);
    }

    public boolean canWalkOnPowderedSnow(ItemStack stack)
    {   return stack.is(ModItems.GOAT_FUR_BOOTS.value());
    }
}