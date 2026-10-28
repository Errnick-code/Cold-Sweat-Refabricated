package com.momosoftworks.coldsweat.core.network;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;

/**
 * Sending helpers that replace NeoForge's {@code PacketDistributor}.
 */
public final class CSNet
{
    private CSNet() {}

    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload payload)
    {   ServerPlayNetworking.send(player, payload);
    }

    public static void sendToPlayers(Collection<ServerPlayer> players, CustomPacketPayload payload)
    {   for (ServerPlayer player : players)
        {   ServerPlayNetworking.send(player, payload);
        }
    }

    public static void sendToAllPlayers(MinecraftServer server, CustomPacketPayload payload)
    {   sendToPlayers(server.getPlayerList().getPlayers(), payload);
    }

    public static void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload)
    {   sendToPlayers(PlayerLookup.tracking(entity), payload);
    }

    public static void sendToPlayersTrackingEntityAndSelf(Entity entity, CustomPacketPayload payload)
    {   if (entity instanceof ServerPlayer player)
        {   sendToPlayer(player, payload);
        }
        sendToPlayersTrackingEntity(entity, payload);
    }

    public static void sendToPlayersTrackingChunk(ServerLevel level, ChunkPos chunk, CustomPacketPayload payload)
    {   sendToPlayers(PlayerLookup.tracking(level, chunk), payload);
    }

    public static void sendToPlayersNear(ServerLevel level, Entity except, Vec3 position, double range, CustomPacketPayload payload)
    {   for (ServerPlayer player : PlayerLookup.around(level, position, range))
        {   if (player != except)
            {   sendToPlayer(player, payload);
            }
        }
    }
}