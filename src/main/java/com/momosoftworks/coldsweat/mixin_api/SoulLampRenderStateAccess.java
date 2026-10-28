package com.momosoftworks.coldsweat.mixin_api;

import com.momosoftworks.coldsweat.client.renderer.SoulLampPose;

public interface SoulLampRenderStateAccess
{
    SoulLampPose coldsweat$getSoulLampPose();

    void coldsweat$setSoulLampPose(SoulLampPose pose);
}
