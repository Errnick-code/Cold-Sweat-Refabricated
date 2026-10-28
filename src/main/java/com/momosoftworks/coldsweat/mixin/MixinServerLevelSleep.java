package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.SleepFinishedTimeEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public class MixinServerLevelSleep
{
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;wakeUpAllPlayers()V"))
    private void coldsweat$onSleepFinished(BooleanSupplier hasTimeLeft, CallbackInfo ci)
    {
        ServerLevel level = (ServerLevel) (Object) this;
        ServerPlayer sleeper = level.players().stream().filter(player -> player.isSleeping()).findFirst().orElse(null);
        ModEvents.post(new SleepFinishedTimeEvent(level, sleeper));
    }
}
