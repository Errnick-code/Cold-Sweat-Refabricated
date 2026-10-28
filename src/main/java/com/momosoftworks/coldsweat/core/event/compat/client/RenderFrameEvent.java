package com.momosoftworks.coldsweat.core.event.compat.client;

import com.momosoftworks.coldsweat.core.event.Event;

public class RenderFrameEvent extends Event
{
    private final float partialTick;

    public RenderFrameEvent(float partialTick)
    {   this.partialTick = partialTick;
    }

    public float getPartialTick()
    {   return partialTick;
    }

    public static final class Pre extends RenderFrameEvent
    {
        public Pre(float partialTick)
        {   super(partialTick);
        }
    }
}
