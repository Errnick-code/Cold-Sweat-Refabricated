package com.momosoftworks.coldsweat.core.event.compat.client;

import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RegisterClientTooltipComponentFactoriesEvent extends Event
{
    public static final Map<Class<?>, Function<?, ClientTooltipComponent>> FACTORIES = new HashMap<>();

    public <T extends net.minecraft.world.inventory.tooltip.TooltipComponent> void register(Class<T> type, Function<T, ClientTooltipComponent> factory)
    {   FACTORIES.put(type, factory);
    }

    @SuppressWarnings("unchecked")
    public static ClientTooltipComponent create(net.minecraft.world.inventory.tooltip.TooltipComponent component)
    {
        Function<Object, ClientTooltipComponent> factory = (Function<Object, ClientTooltipComponent>) FACTORIES.get(component.getClass());
        return factory == null ? null : factory.apply(component);
    }
}
