package com.momosoftworks.coldsweat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.PlayerEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class MixinPlayerBreakSpeed
{
    @ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
    private float coldsweat$breakSpeed(float original, BlockState state)
    {
        Player player = (Player) (Object) this;
        PlayerEvent.BreakSpeed event = ModEvents.post(new PlayerEvent.BreakSpeed(player, state, original));
        return event.isCanceled() ? original : event.getNewSpeed();
    }
}
