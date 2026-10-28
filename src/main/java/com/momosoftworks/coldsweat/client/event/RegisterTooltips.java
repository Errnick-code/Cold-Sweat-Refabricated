package com.momosoftworks.coldsweat.client.event;

import com.momosoftworks.coldsweat.client.gui.tooltip.*;

import com.momosoftworks.coldsweat.core.event.SubscribeEvent;

import com.momosoftworks.coldsweat.core.event.compat.client.RegisterClientTooltipComponentFactoriesEvent;

public class RegisterTooltips
{
    @SubscribeEvent
    public static void registerTooltips(RegisterClientTooltipComponentFactoriesEvent event)
    {
        event.register(SoulspringTooltip.class, tooltip -> new ClientSoulspringTooltip(tooltip.getFuel()));
        event.register(InsulationTooltip.class, tooltip -> new ClientInsulationTooltip(tooltip.getInsulation(), tooltip.getSlot(), tooltip.getItem(), tooltip.isStrikethrough()));
        event.register(ConditionalTooltip.class, tooltip -> new ClientConditionalTooltip(tooltip.getOriginal(), tooltip.getFont(), tooltip.isStrikethrough(), tooltip.getIcon()));
    }
}
