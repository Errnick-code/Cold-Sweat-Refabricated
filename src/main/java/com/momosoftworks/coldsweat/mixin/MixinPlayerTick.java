package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.PlayerTickEvent;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class MixinPlayerTick
{
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void coldsweat$tickPre(CallbackInfo ci)
    {
        Player player = (Player) (Object) this;
        if (player.level().isClientSide()) return;

        PlayerTickEvent.Pre event = ModEvents.post(new PlayerTickEvent.Pre(player));
        if (event.isCanceled())
        {   ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void coldsweat$tickPost(CallbackInfo ci)
    {
        Player player = (Player) (Object) this;
        if (player.level().isClientSide()) return;

        ModEvents.post(new PlayerTickEvent.Post(player));
    }
}
