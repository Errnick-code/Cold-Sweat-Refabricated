package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.HashMap;
import java.util.Map;

public class EntityAttributeCreationEvent extends Event
{
    private final Map<EntityType<?>, AttributeSupplier> attributes = new HashMap<>();

    public Map<EntityType<?>, AttributeSupplier> getAttributes()
    {   return attributes;
    }

    public void put(EntityType<?> type, AttributeSupplier supplier)
    {   attributes.put(type, supplier);
    }
}