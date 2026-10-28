package com.momosoftworks.coldsweat.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.momosoftworks.coldsweat.client.event.RenderLampHand;
import com.momosoftworks.coldsweat.client.renderer.SoulLampPose;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.core.init.ModItems;
import com.momosoftworks.coldsweat.util.ClientOnlyHelper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class MixinSoulLampRendering
{
    @Unique
    private HumanoidModel<?> coldsweat$model = (HumanoidModel<?>) (Object) this;

    @Unique
    private PartPose coldsweat$lampArmPose;

    @Final
    @Shadow
    public ModelPart rightArm;

    @Final
    @Shadow
    public ModelPart leftArm;

    @Shadow
    private void poseRightArm(HumanoidRenderState state) {}

    @Shadow
    private void poseLeftArm(HumanoidRenderState state) {}

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"))
    public void poseIdleLampArm(HumanoidRenderState state, CallbackInfo ci)
    {
        if (!ConfigSettings.POSE_SOULSPRING_LAMP.get()) return;

        SoulLampPose pose = SoulLampPose.of(state);
        if (pose == null) return;

        if (pose.holdingLamp(HumanoidArm.RIGHT) && state.leftArmPose != null && state.leftArmPose.affectsOffhandPose())
        {   this.poseRightArm(state);
        }
        if (pose.holdingLamp(HumanoidArm.LEFT) && state.rightArmPose != null && state.rightArmPose.affectsOffhandPose())
        {   this.poseLeftArm(state);
        }
    }

    @Inject(method = "poseRightArm", at = @At("TAIL"))
    public void poseRightLampArm(HumanoidRenderState state, CallbackInfo ci)
    {
        if (!ConfigSettings.POSE_SOULSPRING_LAMP.get()) return;

        SoulLampPose pose = SoulLampPose.of(state);
        if (pose != null && pose.holdingLamp(HumanoidArm.RIGHT))
        {   RenderLampHand.poseArm(coldsweat$model, state, HumanoidArm.RIGHT);
        }
        else RenderLampHand.lowerArm(coldsweat$model, state, HumanoidArm.RIGHT);
    }

    @Inject(method = "poseLeftArm", at = @At("TAIL"))
    public void poseLeftLampArm(HumanoidRenderState state, CallbackInfo ci)
    {
        if (!ConfigSettings.POSE_SOULSPRING_LAMP.get()) return;

        SoulLampPose pose = SoulLampPose.of(state);
        if (pose != null && pose.holdingLamp(HumanoidArm.LEFT))
        {   RenderLampHand.poseArm(coldsweat$model, state, HumanoidArm.LEFT);
        }
        else RenderLampHand.lowerArm(coldsweat$model, state, HumanoidArm.LEFT);
    }

    @Inject(method = "setupAttackAnimation", at = @At("HEAD"))
    public void storeLampArmPose(HumanoidRenderState state, CallbackInfo ci)
    {
        coldsweat$lampArmPose = null;

        SoulLampPose pose = SoulLampPose.of(state);
        if (pose == null || state.attackTime <= 0 || !pose.isPlayer()) return;

        if (pose.holdingLamp(state.attackArm))
        {   coldsweat$lampArmPose = coldsweat$model.getArm(state.attackArm).storePose();
        }
    }

    @Inject(method = "setupAttackAnimation", at = @At("TAIL"))
    public void swingLampArm(HumanoidRenderState state, CallbackInfo ci)
    {
        if (coldsweat$lampArmPose == null) return;

        coldsweat$model.getArm(state.attackArm).setRotation(coldsweat$lampArmPose.xRot(), coldsweat$lampArmPose.yRot(), coldsweat$lampArmPose.zRot());
        RenderLampHand.swingArm(coldsweat$model, state.attackArm, state.attackTime);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("TAIL"))
    public void offsetLampArms(HumanoidRenderState state, CallbackInfo ci)
    {
        SoulLampPose pose = SoulLampPose.of(state);
        if (pose == null) return;

        RenderLampHand.offsetArm(coldsweat$model, pose, HumanoidArm.RIGHT);
        RenderLampHand.offsetArm(coldsweat$model, pose, HumanoidArm.LEFT);
    }

    @Mixin(ItemInHandLayer.class)
    public static class HeldItem
    {
        @Inject(method = "submitArmWithItem", at = @At("HEAD"), cancellable = true)
        public void hideLampForTwoHandedPose(ArmedEntityRenderState state, ItemStackRenderState item, ItemStack itemStack, HumanoidArm arm,
                                             PoseStack poseStack, SubmitNodeCollector collector, int light, CallbackInfo ci)
        {
            SoulLampPose pose = SoulLampPose.of(state);
            if (pose != null && pose.isLampHidden(arm) && itemStack.is(ModItems.SOULSPRING_LAMP.value()))
            {   ci.cancel();
            }
        }

        @Inject(method = "submitArmWithItem",
                at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER))
        public void shiftLampForSlimArms(ArmedEntityRenderState state, ItemStackRenderState item, ItemStack itemStack, HumanoidArm arm,
                                         PoseStack poseStack, SubmitNodeCollector collector, int light, CallbackInfo ci)
        {
            if (!ConfigSettings.POSE_SOULSPRING_LAMP.get()) return;

            ItemInHandLayer<?, ?> self = (ItemInHandLayer<?, ?>) (Object) this;
            if (itemStack.is(ModItems.SOULSPRING_LAMP.value()) && ClientOnlyHelper.isPlayerModelSlim(self))
            {   poseStack.translate((arm == HumanoidArm.RIGHT ? -0.5 : 0.5) / 16f, 0, 0);
            }
        }
    }
}
