package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ModAttributes
{
    public static final Holder.Reference<Attribute> WORLD_TEMPERATURE = attribute("world_temperature", new RangedAttribute("attribute.world_temperature", Double.NaN, Double.NaN, Double.POSITIVE_INFINITY).setSyncable(true));
    public static final Holder.Reference<Attribute> BASE_BODY_TEMPERATURE = attribute("base_temperature", new RangedAttribute("attribute.base_temperature", Double.NaN, Double.NaN, Double.POSITIVE_INFINITY).setSyncable(true));
    public static final Holder.Reference<Attribute> TEMP_RATE = attribute("temperature_rate", new RangedAttribute("attribute.temperature_rate", Double.NaN, Double.NaN, Double.POSITIVE_INFINITY).setSyncable(true));
    public static final Holder.Reference<Attribute> BURNING_POINT = attribute("burning_point", new RangedAttribute("attribute.burning_point", Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).setSyncable(true));
    public static final Holder.Reference<Attribute> FREEZING_POINT = attribute("freezing_point", new RangedAttribute("attribute.freezing_point", Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).setSyncable(true));
    public static final Holder.Reference<Attribute> HEAT_RESISTANCE = attribute("heat_resistance", new RangedAttribute("attribute.heat_resistance", 0.0D, 0D, 1D).setSyncable(true));
    public static final Holder.Reference<Attribute> COLD_RESISTANCE = attribute("cold_resistance", new RangedAttribute("attribute.cold_resistance", 0.0D, 0D, 1D).setSyncable(true));
    public static final Holder.Reference<Attribute> HEAT_DAMPENING = attribute("heat_dampening", new RangedAttribute("attribute.heat_dampening", 0.0D, Double.NEGATIVE_INFINITY, 1D).setSyncable(true));
    public static final Holder.Reference<Attribute> COLD_DAMPENING = attribute("cold_dampening", new RangedAttribute("attribute.cold_dampening", 0.0D, Double.NEGATIVE_INFINITY, 1D).setSyncable(true));

    private static Holder.Reference<Attribute> attribute(String name, Attribute attribute)
    {   return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, ColdSweat.createKey(name), attribute);
    }
}