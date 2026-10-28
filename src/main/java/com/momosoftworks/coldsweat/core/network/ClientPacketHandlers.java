package com.momosoftworks.coldsweat.core.network;

import com.momosoftworks.coldsweat.core.network.message.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public final class ClientPacketHandlers
{
    private ClientPacketHandlers() {}

    public static void register()
    {
        ClientPlayNetworking.registerGlobalReceiver(BlockDataUpdateMessage.TYPE, (payload, context) ->
        {   BlockDataUpdateMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(ChameleonEatMessage.TYPE, (payload, context) ->
        {   ChameleonEatMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(HearthResetMessage.TYPE, (payload, context) ->
        {   HearthResetMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(ParticleBatchMessage.TYPE, (payload, context) ->
        {   ParticleBatchMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(PlayEntityAttachedSoundMessage.TYPE, (payload, context) ->
        {   PlayEntityAttachedSoundMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(SyncContainerSlotMessage.TYPE, (payload, context) ->
        {   SyncContainerSlotMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(SyncForgeDataMessage.TYPE, (payload, context) ->
        {   SyncForgeDataMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(SyncShearableDataMessage.TYPE, (payload, context) ->
        {   SyncShearableDataMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(SyncConfigSettingsMessage.TYPE, (payload, context) ->
        {   SyncConfigSettingsMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(SyncTemperatureMessage.TYPE, (payload, context) ->
        {   SyncTemperatureMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(SyncTempModifiersMessage.TYPE, (payload, context) ->
        {   SyncTempModifiersMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(EntityMountMessage.TYPE, (payload, context) ->
        {   EntityMountMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(DisableHearthParticlesMessage.TYPE, (payload, context) ->
        {   DisableHearthParticlesMessage.handle(payload, new CSClientContext(context));
        });
        ClientPlayNetworking.registerGlobalReceiver(SyncItemPredicatesMessage.TYPE, (payload, context) ->
        {   SyncItemPredicatesMessage.handle(payload, new CSClientContext(context));
        });
    }
}
