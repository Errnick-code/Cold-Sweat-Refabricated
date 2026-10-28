package com.momosoftworks.coldsweat.core.network;

import com.momosoftworks.coldsweat.core.network.message.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModPacketHandlers
{
    public static final String NETWORK_VERSION = "1";

    public static void registerPayloads()
    {
        PayloadTypeRegistry.clientboundPlay().register(BlockDataUpdateMessage.TYPE, BlockDataUpdateMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ChameleonEatMessage.TYPE, ChameleonEatMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(HearthResetMessage.TYPE, HearthResetMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ParticleBatchMessage.TYPE, ParticleBatchMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(PlayEntityAttachedSoundMessage.TYPE, PlayEntityAttachedSoundMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SyncContainerSlotMessage.TYPE, SyncContainerSlotMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SyncForgeDataMessage.TYPE, SyncForgeDataMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SyncShearableDataMessage.TYPE, SyncShearableDataMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SyncTemperatureMessage.TYPE, SyncTemperatureMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SyncTempModifiersMessage.TYPE, SyncTempModifiersMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(EntityMountMessage.TYPE, EntityMountMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(DisableHearthParticlesMessage.TYPE, DisableHearthParticlesMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SyncConfigSettingsMessage.TYPE, SyncConfigSettingsMessage.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SyncItemPredicatesMessage.TYPE, SyncItemPredicatesMessage.CODEC);

        PayloadTypeRegistry.serverboundPlay().register(ClientConfigAskMessage.TYPE, ClientConfigAskMessage.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(SyncPreferencesMessage.TYPE, SyncPreferencesMessage.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(DisableHearthParticlesMessage.TYPE, DisableHearthParticlesMessage.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(SyncConfigSettingsMessage.TYPE, SyncConfigSettingsMessage.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(SyncItemPredicatesMessage.TYPE, SyncItemPredicatesMessage.CODEC);
    }

    public static void registerServerReceivers()
    {
        ServerPlayNetworking.registerGlobalReceiver(ClientConfigAskMessage.TYPE, (payload, context) ->
        {   ClientConfigAskMessage.handle(payload, new CSServerContext(context));
        });
        ServerPlayNetworking.registerGlobalReceiver(SyncPreferencesMessage.TYPE, (payload, context) ->
        {   SyncPreferencesMessage.handle(payload, new CSServerContext(context));
        });
        ServerPlayNetworking.registerGlobalReceiver(DisableHearthParticlesMessage.TYPE, (payload, context) ->
        {   DisableHearthParticlesMessage.handle(payload, new CSServerContext(context));
        });
        ServerPlayNetworking.registerGlobalReceiver(SyncConfigSettingsMessage.TYPE, (payload, context) ->
        {   SyncConfigSettingsMessage.handle(payload, new CSServerContext(context));
        });
        ServerPlayNetworking.registerGlobalReceiver(SyncItemPredicatesMessage.TYPE, (payload, context) ->
        {   SyncItemPredicatesMessage.handle(payload, new CSServerContext(context));
        });
    }
}