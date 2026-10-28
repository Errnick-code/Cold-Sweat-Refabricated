package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.client.renderer.SoulLampPose;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.mixin_api.SoulLampRenderStateAccess;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingEntityRenderer
{
    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
            at = @At("TAIL"))
    private void coldsweat$extractSoulLampPose(LivingEntity entity, LivingEntityRenderState state, float partialTick, CallbackInfo ci)
    {
        SoulLampPose pose = ConfigSettings.POSE_SOULSPRING_LAMP.get() ? SoulLampPose.create(entity, state, partialTick) : null;
        ((SoulLampRenderStateAccess) state).coldsweat$setSoulLampPose(pose);
    }
}
