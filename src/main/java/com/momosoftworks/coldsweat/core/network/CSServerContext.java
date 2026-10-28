package com.momosoftworks.coldsweat.core.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.player.Player;

/**
 * {@link CSNetContext} implementation backed by Fabric's server-side play context.
 */
public record CSServerContext(ServerPlayNetworking.Context context) implements CSNetContext
{
    @Override
    public Player player()
    {   return this.context.player();
    }

    @Override
    public void enqueueWork(Runnable task)
    {   this.context.server().execute(task);
    }

    @Override
    public CSNetFlow flow()
    {   return CSNetFlow.SERVERBOUND;
    }
}