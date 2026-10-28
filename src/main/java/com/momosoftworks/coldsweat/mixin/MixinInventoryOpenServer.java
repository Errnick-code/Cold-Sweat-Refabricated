package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.PlayerContainerEvent;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class MixinInventoryOpenServer
{
    @Shadow
    public ServerPlayer player;

    @Inject(method = "handlePlayerCommand(Lnet/minecraft/network/protocol/game/ServerboundPlayerCommandPacket;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;resetLastActionTime()V"))
    private void coldsweat$onPlayerInvOpen(ServerboundPlayerCommandPacket packet, CallbackInfo ci)
    {
        if (packet.getAction() == ServerboundPlayerCommandPacket.Action.OPEN_INVENTORY
        && !(player.getVehicle() instanceof AbstractHorse))
        {
            ModEvents.post(new PlayerContainerEvent.Open(player, player.containerMenu));
        }
    }
}
