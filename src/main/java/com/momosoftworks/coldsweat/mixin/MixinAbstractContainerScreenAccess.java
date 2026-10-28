package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.mixin_api.AbstractContainerScreenAccess;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractContainerScreen.class)
public abstract class MixinAbstractContainerScreenAccess implements AbstractContainerScreenAccess
{
    @Shadow protected Slot hoveredSlot;
    @Shadow protected int leftPos;
    @Shadow protected int topPos;

    @Override
    public Slot coldsweat$getHoveredSlot()
    {   return this.hoveredSlot;
    }

    @Override
    public int coldsweat$getLeftPos()
    {   return this.leftPos;
    }

    @Override
    public int coldsweat$getTopPos()
    {   return this.topPos;
    }
}
