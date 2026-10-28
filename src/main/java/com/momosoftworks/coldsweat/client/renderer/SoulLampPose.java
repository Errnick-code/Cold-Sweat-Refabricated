package com.momosoftworks.coldsweat.client.renderer;

import com.momosoftworks.coldsweat.client.event.HandleSoulLampAnim;
import com.momosoftworks.coldsweat.mixin_api.SoulLampRenderStateAccess;
import com.momosoftworks.coldsweat.util.entity.EntityHelper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public record SoulLampPose(boolean rightLamp, boolean leftLamp,
                           float rightRaise, float leftRaise,
                           boolean isPlayer,
                           boolean rightHidden, boolean leftHidden)
{
    public boolean holdingLamp(HumanoidArm arm)
    {   return arm == HumanoidArm.RIGHT ? rightLamp : leftLamp;
    }

    public boolean isLampHidden(HumanoidArm arm)
    {   return arm == HumanoidArm.RIGHT ? rightHidden : leftHidden;
    }

    public float getArmRaise(HumanoidArm arm)
    {   return arm == HumanoidArm.RIGHT ? rightRaise : leftRaise;
    }

    @Nullable
    public static SoulLampPose of(LivingEntityRenderState state)
    {
        SoulLampPose base = ((SoulLampRenderStateAccess) state).coldsweat$getSoulLampPose();
        if (base == null || !(state instanceof HumanoidRenderState humanoid)) return base;

        boolean rightHidden = base.rightLamp && shouldHideLamp(humanoid, HumanoidArm.RIGHT);
        boolean leftHidden = base.leftLamp && shouldHideLamp(humanoid, HumanoidArm.LEFT);
        if (!rightHidden && !leftHidden) return base;

        return new SoulLampPose(rightHidden ? false : base.rightLamp,
                                leftHidden ? false : base.leftLamp,
                                rightHidden ? 0 : base.rightRaise,
                                leftHidden ? 0 : base.leftRaise,
                                base.isPlayer, rightHidden, leftHidden);
    }

    @Nullable
    public static SoulLampPose create(LivingEntity entity, LivingEntityRenderState state, float partialTick)
    {
        boolean rightLamp = EntityHelper.holdingLamp(entity, HumanoidArm.RIGHT);
        boolean leftLamp = EntityHelper.holdingLamp(entity, HumanoidArm.LEFT);
        float rightRaise = HandleSoulLampAnim.getArmRaise(entity, HumanoidArm.RIGHT, partialTick);
        float leftRaise = HandleSoulLampAnim.getArmRaise(entity, HumanoidArm.LEFT, partialTick);

        if (!rightLamp && !leftLamp && rightRaise <= 0.01f && leftRaise <= 0.01f)
        {   return null;
        }
        return new SoulLampPose(rightLamp, leftLamp, rightRaise, leftRaise, entity instanceof Player, false, false);
    }

    private static boolean shouldHideLamp(HumanoidRenderState state, HumanoidArm lampArm)
    {
        HumanoidModel.ArmPose otherPose = lampArm == HumanoidArm.RIGHT ? state.leftArmPose : state.rightArmPose;
        return otherPose != null && otherPose.isTwoHanded() && otherPose != HumanoidModel.ArmPose.CROSSBOW_HOLD;
    }
}
