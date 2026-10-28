package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.core.advancement.trigger.ArmorInsulatedTrigger;
import com.momosoftworks.coldsweat.core.advancement.trigger.BlockAffectTempTrigger;
import com.momosoftworks.coldsweat.core.advancement.trigger.SoulLampFueledTrigger;
import com.momosoftworks.coldsweat.core.advancement.trigger.TemperatureChangedTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModAdvancementTriggers
{
    public static final Ref<TemperatureChangedTrigger> TEMPERATURE_CHANGED = trigger("temperature_changed", new TemperatureChangedTrigger());
    public static final Ref<SoulLampFueledTrigger> SOUL_LAMP_FUELED = trigger("soulspring_lamp_fueled", new SoulLampFueledTrigger());
    public static final Ref<BlockAffectTempTrigger> BLOCK_AFFECTS_TEMP = trigger("block_affects_temperature", new BlockAffectTempTrigger());
    public static final Ref<ArmorInsulatedTrigger> ARMOR_INSULATED = trigger("armor_insulated", new ArmorInsulatedTrigger());

    private static <T extends CriterionTrigger<?>> Ref<T> trigger(String name, T trigger)
    {   return new Ref<>(Registry.register(BuiltInRegistries.TRIGGER_TYPES, ColdSweat.createKey(name), trigger));
    }
}
