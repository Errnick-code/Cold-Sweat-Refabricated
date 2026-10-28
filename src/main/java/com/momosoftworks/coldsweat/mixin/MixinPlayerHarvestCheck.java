package com.momosoftworks.coldsweat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.PlayerEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class MixinPlayerHarvestCheck
{
    @ModifyReturnValue(method = "hasCorrectToolForDrops", at = @At("RETURN"))
    private boolean coldsweat$harvestCheck(boolean original, BlockState state)
    {
        Player player = (Player) (Object) this;
        PlayerEvent.HarvestCheck event = ModEvents.post(new PlayerEvent.HarvestCheck(player, state, original));
        return event.getCanHarvest();
    }
}
