package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.MobEffectEvent;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinMobEffect
{
    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At("HEAD"), cancellable = true)
    private void coldsweat$onEffectAdded(MobEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir)
    {
        MobEffectEvent.Added event = ModEvents.post(new MobEffectEvent.Added(effect, (LivingEntity) (Object) this));
        if (event.isCanceled())
        {   cir.setReturnValue(false);
        }
    }

    @Inject(method = "removeEffect", at = @At("HEAD"), cancellable = true)
    private void coldsweat$onEffectRemove(Holder<MobEffect> effect, CallbackInfoReturnable<Boolean> cir)
    {
        LivingEntity entity = (LivingEntity) (Object) this;
        MobEffectEvent.Remove event = ModEvents.post(new MobEffectEvent.Remove(entity.getEffect(effect), entity));
        if (event.isCanceled())
        {   cir.setReturnValue(false);
        }
    }
}
