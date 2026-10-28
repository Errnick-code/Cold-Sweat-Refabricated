package com.momosoftworks.coldsweat.client.event;

import com.momosoftworks.coldsweat.core.network.CSClientNet;
import com.momosoftworks.coldsweat.api.event.core.registry.EdiblesRegisterEvent;
import com.momosoftworks.coldsweat.config.ConfigLoadingHandler;
import com.momosoftworks.coldsweat.core.init.TempModifierInit;
import com.momosoftworks.coldsweat.core.network.message.ClientConfigAskMessage;
import com.momosoftworks.coldsweat.core.network.message.SyncPreferencesMessage;
import com.momosoftworks.coldsweat.util.serialization.RegistryHelper;
import net.minecraft.client.Minecraft;

import com.momosoftworks.coldsweat.core.event.EventPriority;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.SubscribeEvent;

import com.momosoftworks.coldsweat.core.event.compat.client.ClientPlayerNetworkEvent;
import com.momosoftworks.coldsweat.core.event.compat.EntityJoinLevelEvent;
import com.momosoftworks.coldsweat.core.network.CSClientNet;

public class ClientJoinSetup
{
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onJoin(ClientPlayerNetworkEvent.LoggingIn event)
    {
        // Registry access from the server we're connected to
        if (!Minecraft.getInstance().hasSingleplayerServer())
        {   RegistryHelper.setRegistryAccess(event.getPlayer().connection.registryAccess());
        }
        // Build holders
        ConfigLoadingHandler.fillOptionalHolders(event.getPlayer().connection.registryAccess());
        // Get configs
        CSClientNet.sendToServer(new ClientConfigAskMessage());
        // Rebuild TempModifier registries
        TempModifierInit.buildModifierRegistries();
        ModEvents.post(new EdiblesRegisterEvent());
    }

    @SubscribeEvent
    public static void onEverySpawn(EntityJoinLevelEvent event)
    {
        if (event.getEntity() == Minecraft.getInstance().player)
        {   CSClientNet.sendToServer(SyncPreferencesMessage.create());
        }
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event)
    {
        if (event.getPlayer() == null) return;
        ConfigLoadingHandler.destroyOptionalHolders();
    }
}
