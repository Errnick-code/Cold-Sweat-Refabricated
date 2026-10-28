package com.momosoftworks.coldsweat.common.event;

import com.momosoftworks.coldsweat.api.event.vanilla.ChatComponentClickedEvent;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import com.momosoftworks.coldsweat.core.event.SubscribeEvent;
public class SystemMessageHandler
{
    @SubscribeEvent
    //called when a player clicks a clickable chat message
    public static void onPlayerClickChatMessage(ChatComponentClickedEvent event)
    {
        if (ConfigSettings.SHOW_CREATIVE_WARNING.get()
        && event.getStyle().getClickEvent() instanceof net.minecraft.network.chat.ClickEvent.ChangePage)
        {
            ConfigSettings.SHOW_CREATIVE_WARNING.set(false);
            event.getPlayer().sendSystemMessage(getSystemPrefix()
                                           .append(Component.translatable("message.cold_sweat.disable_feedback").withStyle(ChatFormatting.GRAY)));
        }
    }

    public static MutableComponent getSystemPrefix()
    {   return Component.literal("[").append(Component.translatable("message.cold_sweat.mod_name")).append("]: ").withStyle(ChatFormatting.LIGHT_PURPLE);
    }
}
