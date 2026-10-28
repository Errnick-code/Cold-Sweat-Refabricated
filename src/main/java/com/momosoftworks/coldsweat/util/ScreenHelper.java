package com.momosoftworks.coldsweat.util;

import com.momosoftworks.coldsweat.mixin_api.AbstractContainerScreenAccess;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;

public final class ScreenHelper
{
    private ScreenHelper() {}

    public static Slot getHoveredSlot(AbstractContainerScreen<?> screen)
    {   return ((AbstractContainerScreenAccess) screen).coldsweat$getHoveredSlot();
    }

    public static int getGuiLeft(AbstractContainerScreen<?> screen)
    {   return ((AbstractContainerScreenAccess) screen).coldsweat$getLeftPos();
    }

    public static int getGuiTop(AbstractContainerScreen<?> screen)
    {   return ((AbstractContainerScreenAccess) screen).coldsweat$getTopPos();
    }
}
