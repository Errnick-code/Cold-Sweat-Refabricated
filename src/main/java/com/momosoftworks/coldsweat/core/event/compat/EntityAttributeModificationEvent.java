package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.HashMap;
import java.util.Map;

public class EntityAttributeModificationEvent extends Event
{
    private final Map<EntityType<?>, ModifiableAttributeMap> modifiers = new HashMap<>();

    public ModifiableAttributeMap getAttributes(EntityType<?> type)
    {   return modifiers.computeIfAbsent(type, t -> new ModifiableAttributeMap());
    }

    public void add(EntityType<?> type, Holder<Attribute> attribute, double value)
    {   getAttributes(type).add(attribute, value);
    }

    public static class ModifiableAttributeMap
    {
        private final Map<Holder<Attribute>, Double> values = new HashMap<>();

        public void add(Holder<Attribute> attribute, double value)
        {   values.put(attribute, value);
        }

        public Map<Holder<Attribute>, Double> getValues()
        {   return values;
        }

        public double get(Holder<Attribute> attribute)
        {   return values.getOrDefault(attribute, 0.0);
        }
    }
}