package com.momosoftworks.coldsweat.core.event.compat.client;

import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class RenderGuiEvent extends Event
{
    private final GuiGraphicsExtractor graphics;

    public RenderGuiEvent(GuiGraphicsExtractor graphics)
    {   this.graphics = graphics;
    }

    public GuiGraphicsExtractor getGuiGraphics()
    {   return graphics;
    }

    public static final class Pre extends RenderGuiEvent
    {
        public Pre(GuiGraphicsExtractor graphics)
        {   super(graphics);
        }
    }
}
