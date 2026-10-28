package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.ProjectileImpactEvent;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public class MixinProjectileImpact
{
    @Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
    private void coldsweat$onHit(HitResult hitResult, CallbackInfo ci)
    {
        ProjectileImpactEvent event = ModEvents.post(new ProjectileImpactEvent((Projectile) (Object) this, hitResult));
        if (event.isCanceled())
        {   ci.cancel();
        }
    }
}
