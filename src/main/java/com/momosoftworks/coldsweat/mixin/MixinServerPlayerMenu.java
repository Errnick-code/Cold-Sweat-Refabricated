package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.PlayerContainerEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class MixinServerPlayerMenu
{
    @Inject(method = "initMenu", at = @At("HEAD"))
    private void coldsweat$onMenuOpen(AbstractContainerMenu menu, CallbackInfo ci)
    {
        ModEvents.post(new PlayerContainerEvent.Open((ServerPlayer) (Object) this, menu));
    }

    @Inject(method = "doCloseContainer", at = @At("HEAD"))
    private void coldsweat$onMenuClose(CallbackInfo ci)
    {
        ServerPlayer player = (ServerPlayer) (Object) this;
        ModEvents.post(new PlayerContainerEvent.Close(player, player.containerMenu));
    }
}
