package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.LivingKnockBackEvent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class MixinLivingKnockback
{
    @ModifyVariable(method = "knockback", at = @At("HEAD"), argsOnly = true, index = 1)
    private double coldsweat$onKnockback(double strength)
    {
        LivingKnockBackEvent event = ModEvents.post(new LivingKnockBackEvent((LivingEntity) (Object) this, strength));
        return event.isCanceled() ? 0 : event.getStrength();
    }
}
