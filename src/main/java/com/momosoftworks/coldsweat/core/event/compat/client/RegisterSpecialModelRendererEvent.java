package com.momosoftworks.coldsweat.core.event.compat.client;

import com.mojang.serialization.MapCodec;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.resources.Identifier;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RegisterSpecialModelRendererEvent extends Event
{
    public void register(Identifier id, MapCodec<? extends SpecialModelRenderer.Unbaked<?>> codec)
    {
        try
        {
            Field field = SpecialModelRenderers.class.getDeclaredField("ID_MAPPER");
            field.setAccessible(true);
            Object mapper = field.get(null);
            Method put = mapper.getClass().getMethod("put", Object.class, Object.class);
            put.invoke(mapper, id, codec);
        }
        catch (ReflectiveOperationException e)
        {   throw new IllegalStateException("Could not register special model renderer " + id, e);
        }
    }
}
