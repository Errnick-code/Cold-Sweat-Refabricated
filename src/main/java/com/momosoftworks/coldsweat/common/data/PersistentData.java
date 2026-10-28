package com.momosoftworks.coldsweat.common.data;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public final class PersistentData
{
    public static CompoundTag get(Entity entity)
    {   return ((PersistentDataHolder) entity).coldsweat$getData();
    }

    public static CompoundTag get(BlockEntity blockEntity)
    {   return ((PersistentDataHolder) blockEntity).coldsweat$getData();
    }

    public static CompoundTag get(ItemStack stack)
    {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return data == null ? new CompoundTag() : data.copyTag();
    }

    public static void set(ItemStack stack, CompoundTag tag)
    {   stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public static void putUUID(CompoundTag tag, String key, UUID uuid)
    {   tag.putString(key, uuid.toString());
    }

    public static UUID getUUID(CompoundTag tag, String key)
    {   return UUID.fromString(tag.getStringOr(key, ""));
    }
}