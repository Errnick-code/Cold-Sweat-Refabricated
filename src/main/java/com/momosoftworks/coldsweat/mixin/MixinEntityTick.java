package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.EntityTickEvent;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntityTick
{
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void coldsweat$tickPre(CallbackInfo ci)
    {
        EntityTickEvent.Pre event = ModEvents.post(new EntityTickEvent.Pre((Entity) (Object) this));
        if (event.isCanceled())
        {   ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void coldsweat$tickPost(CallbackInfo ci)
    {
        ModEvents.post(new EntityTickEvent.Post((Entity) (Object) this));
    }
}
