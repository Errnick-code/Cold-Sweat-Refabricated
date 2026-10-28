package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.common.item.ArmorAbilities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public abstract class MixinPowderSnowBlock
{
    @Inject(method = "canEntityWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void coldsweat$powderedSnowArmor(Entity entity, CallbackInfoReturnable<Boolean> cir)
    {
        if (cir.getReturnValue() != null) return;
        if (entity instanceof LivingEntity living && ArmorAbilities.canWalkOnPowderedSnow(living))
        {   cir.setReturnValue(true);
        }
    }
}