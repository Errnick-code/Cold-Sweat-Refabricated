package com.momosoftworks.coldsweat.core.event.compat.client;

import com.mojang.serialization.MapCodec;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.resources.Identifier;

public class RegisterRangeSelectItemModelPropertyEvent extends Event
{
    public void register(Identifier id, MapCodec<? extends RangeSelectItemModelProperty> codec)
    {   RegisterConditionalItemModelPropertyEvent.put(RangeSelectItemModelProperties.class, id, codec);
    }
}
