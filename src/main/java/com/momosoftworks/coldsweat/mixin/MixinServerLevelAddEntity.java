package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.DropsCapture;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.EntityJoinLevelEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public class MixinServerLevelAddEntity
{
    @Inject(method = "addFreshEntity", at = @At("HEAD"))
    private void coldsweat$captureDrops(Entity entity, CallbackInfoReturnable<Boolean> cir)
    {
        if (entity instanceof ItemEntity item)
        {   DropsCapture.capture(item);
        }
    }

    @Inject(method = "addFreshEntity", at = @At("RETURN"))
    private void coldsweat$entityJoinLevel(Entity entity, CallbackInfoReturnable<Boolean> cir)
    {
        if (Boolean.TRUE.equals(cir.getReturnValue()) && !(entity instanceof Player))
        {   ModEvents.post(new EntityJoinLevelEvent(entity, entity.level()));
        }
    }
}
