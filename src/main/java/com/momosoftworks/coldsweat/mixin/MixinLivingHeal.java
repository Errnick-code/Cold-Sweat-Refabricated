package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.LivingHealEvent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class MixinLivingHeal
{
    @ModifyVariable(method = "heal", at = @At("HEAD"), argsOnly = true, index = 1)
    private float coldsweat$onHeal(float amount)
    {
        LivingHealEvent event = ModEvents.post(new LivingHealEvent((LivingEntity) (Object) this, amount));
        return event.isCanceled() ? 0 : event.getAmount();
    }
}
