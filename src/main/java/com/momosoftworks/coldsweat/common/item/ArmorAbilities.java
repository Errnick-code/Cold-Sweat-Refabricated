package com.momosoftworks.coldsweat.common.item;

import com.momosoftworks.coldsweat.util.entity.EntityHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.ItemStack;

public class ArmorAbilities
{
    private ArmorAbilities() {}

    public static boolean canWalkOnPowderedSnow(LivingEntity entity)
    {
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        return boots.getItem() instanceof GoatArmorItem goatBoots && goatBoots.canWalkOnPowderedSnow(boots);
    }

    public static boolean makesPiglinsNeutral(LivingEntity entity)
    {
        for (ItemStack armor : EntityHelper.getArmorSlots(entity))
        {
            if (!armor.isEmpty() && armor.getItem() instanceof HoglinArmorItem hoglinHelmet && hoglinHelmet.makesPiglinsNeutral(armor))
            {   return true;
            }
        }
        return false;
    }

    public static boolean isPiglin(LivingEntity entity)
    {   return entity instanceof Piglin;
    }

    public static boolean isGoat(LivingEntity entity)
    {   return entity instanceof Goat;
    }
}