package com.momosoftworks.coldsweat.core.event.client;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.core.event.EventBus;
import com.momosoftworks.coldsweat.core.event.compat.EntityJoinLevelEvent;
import com.momosoftworks.coldsweat.core.event.compat.PlayerTickEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.ClientPlayerNetworkEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.ClientTickEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.RenderGuiEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.RenderGuiLayerEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.ScreenEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.ViewportEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.resources.Identifier;

public final class ClientEventDispatcher
{
    private ClientEventDispatcher() {}

    public static void register()
    {
        ClientTickEvents.START_CLIENT_TICK.register(client ->
        {
            float partialTick = client.getDeltaTracker().getGameTimeDeltaPartialTick(false);
            EventBus.post(new ViewportEvent.ComputeCameraAngles(partialTick));
            EventBus.post(new ClientTickEvent.Pre());
            if (client.player != null)
            {   EventBus.post(new PlayerTickEvent.Pre(client.player));
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> EventBus.post(new ClientTickEvent.Post()));

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) ->
                EventBus.post(new ClientPlayerNetworkEvent.LoggingIn(client.player)));

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) ->
                EventBus.post(new ClientPlayerNetworkEvent.LoggingOut(client.player)));

        ClientEntityEvents.ENTITY_LOAD.register((entity, world) ->
                EventBus.post(new EntityJoinLevelEvent(entity, world)));

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) ->
        {
            EventBus.post(new ScreenEvent.Init.Post(screen));

            ScreenEvents.afterExtract(screen).register((s, graphics, mouseX, mouseY, tickDelta) ->
                    EventBus.post(new ScreenEvent.Render.Post(s, graphics, mouseX, mouseY)));

            ScreenMouseEvents.beforeMouseClick(screen).register((s, event) ->
                    EventBus.post(new ScreenEvent.MouseButtonPressed.Pre(s, event.x(), event.y(), event.button())));

            ScreenMouseEvents.afterMouseClick(screen).register((s, event, result) ->
            {
                EventBus.post(new ScreenEvent.MouseButtonPressed.Post(s, event.x(), event.y(), event.button()));
                return result;
            });

            ScreenMouseEvents.beforeMouseRelease(screen).register((s, event) ->
                    EventBus.post(new ScreenEvent.MouseButtonReleased.Pre(s, event.x(), event.y(), event.button())));
        });

        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(ColdSweat.MOD_ID, "overlay_pass"), (graphics, deltaTracker) ->
        {
            EventBus.post(new RenderGuiLayerEvent.Pre(graphics));
            EventBus.post(new RenderGuiEvent.Pre(graphics));
        });
    }
}
