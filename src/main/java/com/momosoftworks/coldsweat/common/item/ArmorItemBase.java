package com.momosoftworks.coldsweat.common.item;


import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public class ArmorItemBase extends Item
{
    private final ArmorMaterial material;
    private final ArmorType type;

    public ArmorItemBase(ArmorMaterial material, ArmorType type, Properties properties)
    {   super(properties.humanoidArmor(material, type));
        this.material = material;
        this.type = type;
    }

    public ArmorMaterial getArmorMaterial()
    {   return this.material;
    }

    public ArmorType getArmorType()
    {   return this.type;
    }

    public EquipmentSlot getEquipmentSlot()
    {   return this.type.getSlot();
    }
}