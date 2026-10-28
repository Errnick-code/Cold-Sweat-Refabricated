package com.momosoftworks.coldsweat.core.event.compat.client;

import com.momosoftworks.coldsweat.core.event.Event;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class EntityRenderersEvent extends Event
{
    public static final class RegisterRenderers extends EntityRenderersEvent
    {
        public <E extends Entity> void registerEntityRenderer(EntityType<? extends E> type, EntityRendererProvider<E> provider)
        {   EntityRendererRegistry.register(type, provider);
        }

        public <E extends BlockEntity, S extends net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState>
        void registerBlockEntityRenderer(BlockEntityType<E> type, BlockEntityRendererProvider<? super E, ? super S> provider)
        {   BlockEntityRendererRegistry.register(type, provider);
        }
    }

    public static final class RegisterLayerDefinitions extends EntityRenderersEvent
    {
        public void registerLayerDefinition(ModelLayerLocation location, Supplier<LayerDefinition> supplier)
        {   ModelLayerRegistry.registerModelLayer(location, supplier::get);
        }
    }
}
