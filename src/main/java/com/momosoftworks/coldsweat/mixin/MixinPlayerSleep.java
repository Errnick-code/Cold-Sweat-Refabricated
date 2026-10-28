package com.momosoftworks.coldsweat.mixin;

import com.mojang.datafixers.util.Either;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.CanPlayerSleepEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class MixinPlayerSleep
{
    @Inject(method = "startSleepInBed", at = @At("HEAD"), cancellable = true)
    private void coldsweat$canStartSleeping(BlockPos pos, CallbackInfoReturnable<Either<Player.BedSleepingProblem, Unit>> cir)
    {
        Player player = (Player) (Object) this;
        CanPlayerSleepEvent event = ModEvents.post(new CanPlayerSleepEvent(player, pos, null));
        if (event.getProblem() != null)
        {   cir.setReturnValue(Either.left(event.getProblem()));
        }
    }
}
