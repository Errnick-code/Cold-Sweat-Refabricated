package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.api.event.vanilla.RenderHeartEvent;
import com.momosoftworks.coldsweat.core.event.EventBus;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinHeartRender
{
    @Inject(method = "extractHeart", at = @At("TAIL"))
    private void renderHeart(GuiGraphicsExtractor graphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean blink, boolean halfHeart, CallbackInfo ci)
    {
        EventBus.post(new RenderHeartEvent(graphics, heartType, x, y, hardcore, blink, halfHeart));
    }
}
