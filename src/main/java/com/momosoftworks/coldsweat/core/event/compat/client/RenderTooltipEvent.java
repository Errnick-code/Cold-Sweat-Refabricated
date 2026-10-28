package com.momosoftworks.coldsweat.core.event.compat.client;

import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;

public abstract class RenderTooltipEvent extends Event
{
    private final ItemStack itemStack;
    private final GuiGraphicsExtractor graphics;

    protected RenderTooltipEvent(ItemStack itemStack, GuiGraphicsExtractor graphics)
    {
        this.itemStack = itemStack;
        this.graphics = graphics;
    }

    public ItemStack getItemStack()
    {   return itemStack;
    }

    public GuiGraphicsExtractor getGuiGraphics()
    {   return graphics;
    }

    public static final class Pre extends RenderTooltipEvent implements com.momosoftworks.coldsweat.core.event.Cancellable
    {
        private final int x;
        private final int y;

        public Pre(ItemStack itemStack, GuiGraphicsExtractor graphics, int x, int y)
        {
            super(itemStack, graphics);
            this.x = x;
            this.y = y;
        }

        public int getX()
        {   return x;
        }

        public int getY()
        {   return y;
        }
    }

    public static final class GatherComponents extends RenderTooltipEvent
    {
        private final java.util.List<com.mojang.datafixers.util.Either<net.minecraft.network.chat.FormattedText, net.minecraft.world.inventory.tooltip.TooltipComponent>> tooltipElements;

        public GatherComponents(ItemStack itemStack, GuiGraphicsExtractor graphics,
                                java.util.List<com.mojang.datafixers.util.Either<net.minecraft.network.chat.FormattedText, net.minecraft.world.inventory.tooltip.TooltipComponent>> tooltipElements)
        {
            super(itemStack, graphics);
            this.tooltipElements = tooltipElements;
        }

        public java.util.List<com.mojang.datafixers.util.Either<net.minecraft.network.chat.FormattedText, net.minecraft.world.inventory.tooltip.TooltipComponent>> getTooltipElements()
        {   return tooltipElements;
        }
    }
}
