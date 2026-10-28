package com.momosoftworks.coldsweat.core.event;

import com.momosoftworks.coldsweat.core.event.compat.BlockEvent;
import com.momosoftworks.coldsweat.core.event.compat.EntityJoinLevelEvent;
import com.momosoftworks.coldsweat.core.event.compat.EntityLeaveLevelEvent;
import com.momosoftworks.coldsweat.core.event.compat.LivingIncomingDamageEvent;
import com.momosoftworks.coldsweat.core.event.compat.PlayerEvent;
import com.momosoftworks.coldsweat.core.event.compat.PlayerInteractEvent;
import com.momosoftworks.coldsweat.core.event.compat.ServerAboutToStartEvent;
import com.momosoftworks.coldsweat.core.event.compat.ServerStartedEvent;
import com.momosoftworks.coldsweat.core.event.compat.ServerStartingEvent;
import com.momosoftworks.coldsweat.core.event.compat.ServerStoppedEvent;
import com.momosoftworks.coldsweat.core.event.compat.ServerStoppingEvent;
import com.momosoftworks.coldsweat.core.event.compat.ServerTickEvent;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.InteractionResult;

public final class EventDispatcher
{
    private EventDispatcher()
    {   }

    public static void registerCallbacks()
    {
        ServerLifecycleEvents.SERVER_STARTING.register(server ->
        {
            ServerHolder.setServer(server);
            ConfigLoader.loadConfigs(server);
            ModEvents.post(new ServerAboutToStartEvent(server));
            ModEvents.post(new ServerStartingEvent(server));
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server -> ModEvents.post(new ServerStartedEvent(server)));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> ModEvents.post(new ServerStoppingEvent(server)));
        ServerLifecycleEvents.SERVER_STOPPED.register(server ->
        {
            ModEvents.post(new ServerStoppedEvent(server));
            ServerHolder.setServer(null);
        });

        ServerTickEvents.START_SERVER_TICK.register(server -> ModEvents.post(new ServerTickEvent.Pre(server)));
        ServerTickEvents.END_SERVER_TICK.register(server -> ModEvents.post(new ServerTickEvent.Post(server)));

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                ModEvents.post(new EntityJoinLevelEvent(handler.getPlayer(), handler.getPlayer().level())));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) ->
                ModEvents.post(new EntityLeaveLevelEvent(handler.getPlayer(), handler.getPlayer().level())));

        EntityTrackingEvents.START_TRACKING.register((entity, player) ->
                ModEvents.post(new PlayerEvent.StartTracking(player, entity)));
        EntityTrackingEvents.STOP_TRACKING.register((entity, player) ->
                ModEvents.post(new PlayerEvent.StopTracking(player, entity)));

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) ->
        {
            LivingIncomingDamageEvent event = ModEvents.post(new LivingIncomingDamageEvent(entity, source, amount));
            return !event.isCanceled();
        });

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, wasDeath) -> ModEvents.post(new PlayerEvent.Clone(newPlayer, oldPlayer, !oldPlayer.isAlive())));
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> ModEvents.post(new EntityJoinLevelEvent(newPlayer, newPlayer.level())));

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) ->
        {
            BlockEvent.BreakEvent event = ModEvents.post(new BlockEvent.BreakEvent(world, pos, state, player));
            return !event.isCanceled();
        });

        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            PlayerInteractEvent.RightClickItem event = ModEvents.post(new PlayerInteractEvent.RightClickItem(player, hand));
            return event.isCanceled() ? event.getCancellationResult() : InteractionResult.PASS;
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
        {
            PlayerInteractEvent.EntityInteract event = ModEvents.post(new PlayerInteractEvent.EntityInteract(player, hand, entity));
            return event.isCanceled() ? event.getCancellationResult() : InteractionResult.PASS;
        });
    }
}