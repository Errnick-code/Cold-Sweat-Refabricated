package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.LevelTickEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ClientLevel.class)
public class MixinClientLevelTick
{
    @Inject(method = "tick", at = @At("HEAD"))
    private void coldsweat$levelTickPre(BooleanSupplier hasTimeLeft, CallbackInfo ci)
    {
        ModEvents.post(new LevelTickEvent.Pre((ClientLevel) (Object) this));
    }
}
