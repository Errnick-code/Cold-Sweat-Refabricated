package com.momosoftworks.coldsweat.core.event.compat.client;

import com.momosoftworks.coldsweat.core.event.Event;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.resources.Identifier;

public class RegisterGuiLayersEvent extends Event
{
    public void registerAbove(Identifier otherLayer, Identifier id, GuiLayer layer)
    {   HudElementRegistry.attachElementAfter(otherLayer, id, layer);
    }

    public void registerBelow(Identifier otherLayer, Identifier id, GuiLayer layer)
    {   HudElementRegistry.attachElementBefore(otherLayer, id, layer);
    }

    public void registerFirst(Identifier id, GuiLayer layer)
    {   HudElementRegistry.addFirst(id, layer);
    }

    public void registerLast(Identifier id, GuiLayer layer)
    {   HudElementRegistry.addLast(id, layer);
    }
}
