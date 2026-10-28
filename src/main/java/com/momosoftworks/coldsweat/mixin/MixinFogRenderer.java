package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.EventBus;
import com.momosoftworks.coldsweat.core.event.compat.client.ViewportEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FogRenderer.class)
public class MixinFogRenderer
{
    @Inject(method = "setupFog", at = @At("RETURN"), cancellable = true)
    private void coldsweat$modifyFog(Camera camera, int renderDistance, DeltaTracker deltaTracker, float partialTick,
                                     ClientLevel level, CallbackInfoReturnable<FogData> cir)
    {
        FogData data = cir.getReturnValue();
        if (data == null) return;

        ViewportEvent.RenderFog fog = new ViewportEvent.RenderFog(data.environmentalStart, data.environmentalEnd);
        EventBus.post(fog);
        data.environmentalStart = fog.getNearPlaneDistance();
        data.environmentalEnd = fog.getFarPlaneDistance();

        Vector4f color = data.color;
        ViewportEvent.ComputeFogColor fogColor = new ViewportEvent.ComputeFogColor(color.x, color.y, color.z);
        EventBus.post(fogColor);
        color.set(fogColor.getRed(), fogColor.getGreen(), fogColor.getBlue(), color.w);
    }
}
