package com.momosoftworks.coldsweat.core.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.common.capability.handler.EntityTempManager;
import com.momosoftworks.coldsweat.common.capability.shearing.IShearableCap;
import com.momosoftworks.coldsweat.common.capability.shearing.ShearableFurCap;
import com.momosoftworks.coldsweat.common.capability.temperature.AbstractTempCap;
import com.momosoftworks.coldsweat.common.capability.temperature.EntityTempCap;
import com.momosoftworks.coldsweat.common.capability.temperature.ITemperatureCap;
import com.momosoftworks.coldsweat.common.capability.temperature.PlayerTempCap;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.player.Player;

public class ModDataAttachments
{
    private static final Codec<ITemperatureCap> TEMPERATURE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("player", false).forGetter(cap -> cap instanceof PlayerTempCap),
            CompoundTag.CODEC.optionalFieldOf("data", new CompoundTag())
                    .forGetter(cap -> cap instanceof AbstractTempCap tempCap ? tempCap.serializeNBT(null) : new CompoundTag())
    ).apply(instance, (player, data) ->
    {
        AbstractTempCap cap = player ? new PlayerTempCap() : new EntityTempCap();
        cap.deserializeNBT(null, data);
        return cap;
    }));

    private static final Codec<IShearableCap> SHEARABLE_CODEC = CompoundTag.CODEC.xmap(
            tag -> { ShearableFurCap cap = new ShearableFurCap(); cap.deserializeNBT(null, tag); return cap; },
            cap -> cap.serializeNBT(null));

    public static final AttachmentType<ITemperatureCap> ENTITY_TEMPERATURE =
            AttachmentRegistry.<ITemperatureCap>builder()
                    .initializer(PlayerTempCap::new)
                    .persistent(TEMPERATURE_CODEC)
                    .buildAndRegister(ColdSweat.createKey("temperature"));

    public static final AttachmentType<IShearableCap> SHEARABLE_FUR =
            AttachmentRegistry.<IShearableCap>builder()
                    .initializer(ShearableFurCap::new)
                    .persistent(SHEARABLE_CODEC)
                    .buildAndRegister(ColdSweat.createKey("shearable_fur"));

    public static AttachmentType<ITemperatureCap> temperatureAttachment()
    {   return ENTITY_TEMPERATURE;
    }

    public static AttachmentType<IShearableCap> shearableAttachment()
    {   return SHEARABLE_FUR;
    }

    public static ITemperatureCap initTemperature(ITemperatureCap cap)
    {
        return cap;
    }

    public static boolean hasShearableTarget(Object target)
    {   return target instanceof Goat;
    }

    public static boolean isTemperatureAttached(LivingEntity entity)
    {   return EntityTempManager.isTemperatureEnabled(entity);
    }

    public static boolean isPlayer(Object target)
    {   return target instanceof Player;
    }

    public static ITemperatureCap createEntityCap()
    {   return new EntityTempCap();
    }
}
