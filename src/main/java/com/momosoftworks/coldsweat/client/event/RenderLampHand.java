package com.momosoftworks.coldsweat.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.momosoftworks.coldsweat.client.renderer.SoulLampPose;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.core.init.ModItems;
import com.momosoftworks.coldsweat.util.ClientOnlyHelper;
import com.momosoftworks.coldsweat.util.entity.EntityHelper;
import com.momosoftworks.coldsweat.util.math.CSMath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RenderLampHand
{
    private static final float MAX_ARM_RAISE = CSMath.toRadians(70);

    private static final float FIRST_PERSON_ARM_Y_ROT = -0.0148f;
    private static final float FIRST_PERSON_ARM_Z_ROT = (float) (Math.PI / 2) + 0.05f;

    private static final boolean[] HAND_RAISED = new boolean[2];

    public static void renderFirstPerson(PoseStack ms, SubmitNodeCollector collector, int light, LocalPlayer player,
                                         ItemStack stack, InteractionHand hand, float swingProgress, float equipProgress)
    {
        if (player == null) return;

        boolean isRightHand = EntityHelper.getArmFromHand(hand, player) == HumanoidArm.RIGHT;
        float handX = isRightHand ? 1 : -1;

        int handIndex = hand.ordinal();
        if (stack != player.getItemInHand(hand) || player.isHandsBusy())
        {   HAND_RAISED[handIndex] = false;
        }
        else if (equipProgress == 0)
        {   HAND_RAISED[handIndex] = true;
        }
        if (HAND_RAISED[handIndex])
        {   equipProgress = 0;
        }

        ms.pushPose();

        float sqrtSwing = Mth.sqrt(swingProgress);
        float handSwingX = -0.3F * Mth.sin(sqrtSwing * (float) Math.PI);
        float handSwingY = 0.1F * Mth.sin(sqrtSwing * (float) Math.PI * 2);
        float handSwingZ = -0.0167F * Mth.sin(swingProgress * (float) Math.PI);
        ms.translate(handX * (handSwingX + 0.64F), handSwingY + 0.2F - equipProgress * 0.6F, handSwingZ - 0.32F);
        ms.mulPose(Axis.YP.rotationDegrees(handX * 45));

        float handFlailX = Mth.sin(sqrtSwing * (float) Math.PI);
        float handFlailZ = Mth.sin(swingProgress * swingProgress * (float) Math.PI);
        ms.mulPose(Axis.YP.rotationDegrees(handX * handFlailX));
        ms.mulPose(Axis.XP.rotationDegrees(handFlailX * -10));
        ms.mulPose(Axis.ZP.rotationDegrees(handX * handFlailZ * 20));

        ms.mulPose(Axis.XP.rotationDegrees(90));
        ms.mulPose(Axis.ZP.rotationDegrees(handX * -130));
        ms.scale(0.5f, 0.5f, 0.5f);
        ms.translate(handX * 0.5, -0.1, 0.5);

        ms.pushPose();
        ms.scale(1, 1.2f, 1);
        ms.mulPose(Axis.XP.rotationDegrees(-25));
        ms.translate(handX * -0.365, -0.4, 0.175);
        ms.mulPose(Axis.YP.rotationDegrees(handX * -90));
        ms.mulPose(Axis.ZP.rotationDegrees(handX * -90));
        AvatarRenderer<?> playerRenderer = (AvatarRenderer<?>) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player);
        renderArm(ms, collector, light, player, isRightHand, playerRenderer);
        ms.popPose();

        ms.pushPose();
        ms.mulPose(Axis.XP.rotationDegrees(-90));
        ms.translate(handX * -0.35, 0.1, 0.625);
        ms.scale(1, 1, 0.8f);
        ms.mulPose(Axis.ZP.rotationDegrees(90));
        ms.translate(-0.1, 0.125, 0);
        Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(player, stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, ms, collector, light);
        ms.popPose();

        ms.popPose();
    }

    private static void renderArm(PoseStack ms, SubmitNodeCollector collector, int light, LocalPlayer player, boolean isRightHand,
                                  AvatarRenderer<?> playerRenderer)
    {
        HumanoidArm side = isRightHand ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
        PlayerModel model = playerRenderer.getModel();
        ModelPart arm = model.getArm(side);
        ModelPart sleeve = isRightHand ? model.rightSleeve : model.leftSleeve;
        float sideMultiplier = isRightHand ? 1 : -1;

        arm.resetPose();
        arm.visible = true;
        arm.setPos(-6 * sideMultiplier, ClientOnlyHelper.isPlayerModelSlim(model) ? 2 : 3, 0);
        arm.setRotation(0, FIRST_PERSON_ARM_Y_ROT * sideMultiplier, FIRST_PERSON_ARM_Z_ROT * sideMultiplier);
        sleeve.visible = player.isModelPartShown(isRightHand ? PlayerModelPart.RIGHT_SLEEVE : PlayerModelPart.LEFT_SLEEVE);

        Identifier skin = player.getSkin().body().texturePath();
        collector.submitModelPart(arm, ms, RenderTypes.entityTranslucent(skin), light, OverlayTexture.NO_OVERLAY, null);
    }

    public static void poseArm(HumanoidModel<?> model, HumanoidRenderState state, HumanoidArm side)
    {
        ModelPart arm = model.getArm(side);
        float sideMultiplier = side == HumanoidArm.RIGHT ? 1 : -1;
        SoulLampPose pose = SoulLampPose.of(state);
        float raise = (pose != null ? pose.getArmRaise(side) : 0) * MAX_ARM_RAISE;

        float armHeight = (arm.xRot * 0.5f - (float) Math.PI / 10) * 0.15f - 0.35f - raise;
        arm.xRot = (float) -Math.PI / 2;
        arm.yRot = -(armHeight + (float) Math.PI / 2 * 1.04f) * sideMultiplier;
        arm.zRot = ((float) Math.PI / 2 - 0.05f) * sideMultiplier;
        if (state.isCrouching)
        {   arm.xRot -= 0.4f;
        }
    }

    public static void lowerArm(HumanoidModel<?> model, HumanoidRenderState state, HumanoidArm side)
    {
        SoulLampPose pose = SoulLampPose.of(state);
        if (pose == null || pose.holdingLamp(side)) return;

        HumanoidModel.ArmPose armPose = side == HumanoidArm.RIGHT ? state.rightArmPose : state.leftArmPose;
        float raise = pose.getArmRaise(side) * MAX_ARM_RAISE;
        if (raise > 0.01f && (armPose == HumanoidModel.ArmPose.EMPTY || armPose == HumanoidModel.ArmPose.ITEM))
        {   model.getArm(side).xRot -= raise;
        }
    }

    public static void swingArm(HumanoidModel<?> model, HumanoidArm side, float attackAnim)
    {
        ModelPart arm = model.getArm(side);
        float sideMultiplier = side == HumanoidArm.RIGHT ? 1 : -1;
        float pitch = CSMath.toDegrees(model.head.xRot);
        float swingSin = Mth.sin(attackAnim * (float) Math.PI);

        float windUp = getWindUp(attackAnim) / getSwingHorizontalOffset(side, pitch);
        float sweep = Mth.sin((1 - (float) Math.pow(1 - attackAnim, 4)) * (float) Math.PI) * 1.2f
                    + swingSin * (0.7f - model.head.xRot) * 0.75f;
        arm.xRot += windUp - sweep;

        float pitchSwingHeight = pitch < 0 ? pitch/20 : pitch/60;
        arm.yRot += (float) (Math.pow(attackAnim - 0.5, 2) - 0.25) * pitchSwingHeight * sideMultiplier;
        arm.zRot += swingSin * (side == HumanoidArm.RIGHT ? -0.4f : 0.1f);

        arm.yRot += model.body.yRot * 3;
        if (side == HumanoidArm.LEFT)
        {   arm.xRot += model.body.yRot;
        }
    }

    public static void offsetArm(HumanoidModel<?> model, SoulLampPose pose, HumanoidArm side)
    {
        if (!pose.isPlayer() || !pose.holdingLamp(side)) return;

        ModelPart arm = model.getArm(side);
        arm.x -= side == HumanoidArm.RIGHT ? 1 : -1;
        if (model instanceof PlayerModel && !ClientOnlyHelper.isPlayerModelSlim(model))
        {   arm.y += 1;
        }
    }

    private static float getWindUp(float attackAnim)
    {
        float windUpTime = 0.3f;
        float windUpPoint = 1.5f;
        float midSwingTime = 0.7f;

        if (attackAnim < windUpTime / 3)
        {   return CSMath.blendLog(0f, windUpPoint, attackAnim, 0, windUpTime / 3, 1);
        }
        else if (attackAnim < windUpTime)
        {   return CSMath.blendLog(windUpPoint, 1f, attackAnim, windUpTime / 3, windUpTime, 1);
        }
        else if (attackAnim < midSwingTime)
        {   return CSMath.blend(1f, 0.2f, attackAnim, windUpTime, midSwingTime);
        }
        else
        {   return CSMath.blendExp(0.2f, 0f, attackAnim, midSwingTime, 1);
        }
    }

    private static float getSwingHorizontalOffset(HumanoidArm side, float playerPitch)
    {
        float pitchFactor;
        if (side == HumanoidArm.RIGHT)
        {
            pitchFactor = playerPitch < 0 ? CSMath.blend(1.8f, 0.6f, playerPitch, 0, -90)
                                          : CSMath.blend(1.8f, 3f, playerPitch, 0, 90);
        }
        else
        {
            pitchFactor = playerPitch < 0 ? CSMath.blend(3f, 0.5f, playerPitch, 0, -90)
                                          : CSMath.blend(3f, 3f, playerPitch, 0, 90);
        }
        return pitchFactor;
    }
}
