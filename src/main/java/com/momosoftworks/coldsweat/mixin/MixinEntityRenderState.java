package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.client.renderer.SoulLampPose;
import com.momosoftworks.coldsweat.mixin_api.GoatRenderStateAccess;
import com.momosoftworks.coldsweat.mixin_api.SoulLampRenderStateAccess;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityRenderState.class)
public class MixinEntityRenderState implements GoatRenderStateAccess, SoulLampRenderStateAccess
{
    @Unique
    private boolean coldsweat$goatSheared;

    @Unique
    private SoulLampPose coldsweat$soulLampPose;

    @Override
    public boolean coldsweat$isSheared()
    {   return coldsweat$goatSheared;
    }

    @Override
    public void coldsweat$setSheared(boolean sheared)
    {   coldsweat$goatSheared = sheared;
    }

    @Override
    public SoulLampPose coldsweat$getSoulLampPose()
    {   return coldsweat$soulLampPose;
    }

    @Override
    public void coldsweat$setSoulLampPose(SoulLampPose pose)
    {   coldsweat$soulLampPose = pose;
    }
}
