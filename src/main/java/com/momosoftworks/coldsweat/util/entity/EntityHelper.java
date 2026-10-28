package com.momosoftworks.coldsweat.util.entity;

import com.momosoftworks.coldsweat.core.init.ModItems;
import com.mojang.datafixers.util.Pair;
import com.momosoftworks.coldsweat.common.item.SoulspringLampItem;
import com.momosoftworks.coldsweat.util.math.MappedCache;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EntityHelper
{
    private static final List<EquipmentSlot> ARMOR_SLOTS = List.of(
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);

    private static final MappedCache<Entity, Pair<CompoundTag, Long>> ENTITY_DATA_CACHE = new MappedCache<>(entity -> Pair.of(saveEntity(entity), System.currentTimeMillis()), Entity::isRemoved);

    public static net.minecraft.world.entity.ai.goal.GoalSelector getGoalSelector(net.minecraft.world.entity.Mob mob)
    {   try
        {   java.lang.reflect.Field field = net.minecraft.world.entity.Mob.class.getDeclaredField("goalSelector");
            field.setAccessible(true);
            return (net.minecraft.world.entity.ai.goal.GoalSelector) field.get(mob);
        }
        catch (Exception e)
        {   throw new RuntimeException(e);
        }
    }

    private static CompoundTag saveEntity(Entity entity)
    {   net.minecraft.world.level.storage.TagValueOutput output = net.minecraft.world.level.storage.TagValueOutput.createWithContext(net.minecraft.util.ProblemReporter.DISCARDING, entity.registryAccess());
        entity.saveWithoutId(output);
        return output.buildResult();
    }

    private EntityHelper() {}

    public static ItemStack getItemInHand(LivingEntity player, HumanoidArm hand)
    {   return player.getItemInHand(hand == player.getMainArm() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
    }

    public static HumanoidArm getArmFromHand(InteractionHand hand, Player player)
    {   return hand == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm() == HumanoidArm.RIGHT ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }

    public static boolean holdingLamp(LivingEntity player, HumanoidArm arm)
    {   return getItemInHand(player, arm).getItem() == ModItems.SOULSPRING_LAMP.value();
    }

    public static boolean holdingLitLamp(LivingEntity player)
    {   return Stream.of(HumanoidArm.LEFT, HumanoidArm.RIGHT).anyMatch(arm -> holdingLamp(player, arm) && SoulspringLampItem.isLit(getItemInHand(player, arm)));
    }

    public static Vec3 getCenterOf(Entity entity)
    {   return entity.position().add(0, entity.getBbHeight() / 2, 0);
    }

    public static ServerPlayer getServerPlayer(Player player)
    {   MinecraftServer server = player.level().getServer();
        return server == null ? null : server.getPlayerList().getPlayer(player.getUUID());
    }

    public static int getInventorySlot(Entity entity, ItemStack stack)
    {
        if (entity instanceof Player player)
        {
            net.minecraft.world.entity.player.Inventory inventory = player.getInventory();
            for (int i = 0; i < inventory.getContainerSize(); i++)
            {
                if (inventory.getItem(i) == stack)
                {   return i;
                }
            }
        }
        return -1;
    }

    @Nullable
    public static EquipmentSlot getEquipmentSlot(int index)
    {        if (index == 100 + EquipmentSlot.HEAD.getIndex())
        {   return EquipmentSlot.HEAD;
        }
        else if (index == 100 + EquipmentSlot.CHEST.getIndex())
        {   return EquipmentSlot.CHEST;
        }
        else if (index == 100 + EquipmentSlot.LEGS.getIndex())
        {   return EquipmentSlot.LEGS;
        }
        else if (index == 100 + EquipmentSlot.FEET.getIndex())
        {   return EquipmentSlot.FEET;
        }
        else if (index == 98)
        {   return EquipmentSlot.MAINHAND;
        }
        else
        {   return index == 99 ? EquipmentSlot.OFFHAND : null;
        }
    }

    public static CompoundTag getFullData(Entity entity)
    {
        long time = System.currentTimeMillis();
        Pair<CompoundTag, Long> pair = ENTITY_DATA_CACHE.get(entity);
        if (time - pair.getSecond() < 1000)
        {   return pair.getFirst();
        }
        else return ENTITY_DATA_CACHE.getFresh(entity).getFirst();
    }

    public static List<ItemStack> getArmorSlots(LivingEntity entity)
    {   return ARMOR_SLOTS.stream().map(entity::getItemBySlot).toList();
    }

    public static void forEachArmorSlot(LivingEntity entity, Consumer<ItemStack> consumer)
    {   ARMOR_SLOTS.forEach(slot -> consumer.accept(entity.getItemBySlot(slot)));
    }
}