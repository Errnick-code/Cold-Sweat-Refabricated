package com.momosoftworks.coldsweat.mixin_api;

import net.minecraft.world.inventory.Slot;

public interface AbstractContainerScreenAccess
{
    Slot coldsweat$getHoveredSlot();

    int coldsweat$getLeftPos();

    int coldsweat$getTopPos();
}
