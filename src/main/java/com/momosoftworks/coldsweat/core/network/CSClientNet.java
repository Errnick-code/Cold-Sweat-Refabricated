package com.momosoftworks.coldsweat.core.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * Client-side counterpart to {@link CSNet} for packets sent from the client to the server.
 */
public final class CSClientNet
{
    private CSClientNet() {}

    public static void sendToServer(CustomPacketPayload payload)
    {   ClientPlayNetworking.send(payload);
    }
}
