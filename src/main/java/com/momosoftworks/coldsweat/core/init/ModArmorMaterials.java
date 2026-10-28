package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.data.tag.ModItemTags;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;
import java.util.Map;

public class ModArmorMaterials
{
    public static final Ref<ArmorMaterial> HOGLIN = material("hoglin",
            new ArmorMaterial(25, defense(3, 6, 5, 2), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 1.5F, 0.0F,
                    ModItemTags.HOGLIN_LEATHERS, asset("hoglin")));

    public static final Ref<ArmorMaterial> GOAT_FUR = material("goat_fur",
            new ArmorMaterial(15, defense(2, 5, 4, 1), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F,
                    ModItemTags.GOAT_FURS, asset("goat_fur")));

    public static final Ref<ArmorMaterial> CHAMELEON = material("chameleon",
            new ArmorMaterial(15, defense(2, 6, 5, 2), 15, Holder.direct(ModSounds.ARMOR_EQUIP_CHAMELEON.value()), 0.0F, 0.0F,
                    ModItemTags.CHAMELEON_SCALES, asset("chameleon")));

    private static Map<ArmorType, Integer> defense(int helmet, int chestplate, int leggings, int boots)
    {
        EnumMap<ArmorType, Integer> map = new EnumMap<>(ArmorType.class);
        map.put(ArmorType.HELMET, helmet);
        map.put(ArmorType.CHESTPLATE, chestplate);
        map.put(ArmorType.LEGGINGS, leggings);
        map.put(ArmorType.BOOTS, boots);
        return map;
    }

    private static ResourceKey<EquipmentAsset> asset(String name)
    {   return ResourceKey.create(EquipmentAssets.ROOT_ID, ColdSweat.createKey(name));
    }

    private static Ref<ArmorMaterial> material(String name, ArmorMaterial material)
    {   return new Ref<>(material);
    }
}