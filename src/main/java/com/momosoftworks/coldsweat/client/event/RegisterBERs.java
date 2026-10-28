package com.momosoftworks.coldsweat.client.event;

import com.momosoftworks.coldsweat.client.renderer.block.HearthBlockEntityRenderer;
import com.momosoftworks.coldsweat.client.renderer.block.IceboxBlockEntityRenderer;
import com.momosoftworks.coldsweat.core.init.ModBlockEntities;

import com.momosoftworks.coldsweat.core.event.SubscribeEvent;

import com.momosoftworks.coldsweat.core.event.compat.client.EntityRenderersEvent;

public class RegisterBERs
{
    @SubscribeEvent
    public static void registerBERs(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntities.ICEBOX.get(), IceboxBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.HEARTH.get(), HearthBlockEntityRenderer::new);
    }
}
