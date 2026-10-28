package com.momosoftworks.coldsweat.core.init;

import com.mojang.serialization.Codec;
import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.common.capability.insulation.ItemInsulationCap;
import com.momosoftworks.coldsweat.data.item_component.SoulspringLampData;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

public class ModItemComponents
{
    public static final DataComponentType<SoulspringLampData> SOULSPRING_LAMP_DATA = component("soulspring_data",
            DataComponentType.<SoulspringLampData>builder().persistent(SoulspringLampData.CODEC).networkSynchronized(ByteBufCodecs.fromCodec(SoulspringLampData.CODEC)).ignoreSwapAnimation().build());

    public static final DataComponentType<ItemInsulationCap> ARMOR_INSULATION = component("armor_insulation",
            DataComponentType.<ItemInsulationCap>builder().persistent(ItemInsulationCap.CODEC).networkSynchronized(ItemInsulationCap.STREAM_CODEC).build());

    public static final DataComponentType<Double> ARMOR_ADAPTATION = component("armor_adaptation",
            DataComponentType.<Double>builder().persistent(Codec.DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE).build());

    public static final DataComponentType<Double> WATER_TEMPERATURE = component("temperature",
            DataComponentType.<Double>builder().persistent(Codec.DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE).ignoreSwapAnimation().build());

    @Deprecated(since = "2.4", forRemoval = true)
    public static final DataComponentType<Double> SOULSPRING_LAMP_FUEL = component("fuel",
            DataComponentType.<Double>builder().persistent(Codec.DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE).ignoreSwapAnimation().build());

    private static <T> DataComponentType<T> component(String name, DataComponentType<T> type)
    {   return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ColdSweat.createKey(name), type);
    }
}
