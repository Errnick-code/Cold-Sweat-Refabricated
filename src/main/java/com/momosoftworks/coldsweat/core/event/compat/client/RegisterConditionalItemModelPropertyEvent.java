package com.momosoftworks.coldsweat.core.event.compat.client;

import com.mojang.serialization.MapCodec;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.resources.Identifier;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RegisterConditionalItemModelPropertyEvent extends Event
{
    public void register(Identifier id, MapCodec<? extends ConditionalItemModelProperty> codec)
    {   put(ConditionalItemModelProperties.class, id, codec);
    }

    static void put(Class<?> owner, Identifier id, MapCodec<?> codec)
    {
        try
        {
            Field field = owner.getDeclaredField("ID_MAPPER");
            field.setAccessible(true);
            Object mapper = field.get(null);
            Method put = mapper.getClass().getMethod("put", Object.class, Object.class);
            put.invoke(mapper, id, codec);
        }
        catch (ReflectiveOperationException e)
        {   throw new IllegalStateException("Could not register item model property " + id, e);
        }
    }
}
