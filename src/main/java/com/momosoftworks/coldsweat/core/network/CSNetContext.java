package com.momosoftworks.coldsweat.core.network;

import net.minecraft.world.entity.player.Player;

/**
 * Abstraction over the networking context provided by the loader, replacing NeoForge's
 * {@code IPayloadContext}. The original API exposed {@code player()}, {@code enqueueWork(Runnable)}
 * and a payload flow with direction/reception-side queries.
 */
public interface CSNetContext
{
    Player player();

    void enqueueWork(Runnable task);

    CSNetFlow flow();

    default boolean isServerbound()
    {   return this.flow().isServerbound();
    }

    default boolean isClientbound()
    {   return this.flow().isClientbound();
    }

    default boolean isServerSide()
    {   return this.player().level().isClientSide() == false;
    }

    default boolean isClientSide()
    {   return this.player().level().isClientSide();
    }
}