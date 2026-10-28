package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.LivingEntityUseItemEvent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingUseItemStop
{
    @Inject(method = "stopUsingItem", at = @At("HEAD"), cancellable = true)
    private void coldsweat$onStopUsingItem(CallbackInfo ci)
    {
        LivingEntity entity = (LivingEntity) (Object) this;
        LivingEntityUseItemEvent.Stop event = ModEvents.post(
                new LivingEntityUseItemEvent.Stop(entity, entity.getUseItem(), entity.getUseItemRemainingTicks()));
        if (event.isCanceled())
        {   ci.cancel();
        }
    }
}
