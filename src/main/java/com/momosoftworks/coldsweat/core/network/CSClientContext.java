package com.momosoftworks.coldsweat.core.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.entity.player.Player;

/**
 * {@link CSNetContext} implementation backed by Fabric's client-side play context.
 */
public record CSClientContext(ClientPlayNetworking.Context context) implements CSNetContext
{
    @Override
    public Player player()
    {   return this.context.player();
    }

    @Override
    public void enqueueWork(Runnable task)
    {   this.context.client().execute(task);
    }

    @Override
    public CSNetFlow flow()
    {   return CSNetFlow.CLIENTBOUND;
    }
}
