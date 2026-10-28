package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.config.ConfigSettings;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinXPBar
{
    @Inject(method = "extractSelectedItemName(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V", at = @At("HEAD"))
    public void renderItemNamePre1(GuiGraphicsExtractor graphics, CallbackInfo ci)
    {
        if (ConfigSettings.CUSTOM_HOTBAR_LAYOUT.get())
        {   graphics.pose().pushMatrix();
            graphics.pose().translate(0, -4);
        }
    }

    @Inject(method = "extractSelectedItemName(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V", at = @At("TAIL"))
    public void renderItemNamePre2(GuiGraphicsExtractor graphics, CallbackInfo ci)
    {
        if (ConfigSettings.CUSTOM_HOTBAR_LAYOUT.get())
        {   graphics.pose().popMatrix();
        }
    }

    @Mixin(ExperienceBarRenderer.class)
    public static class MixinExperienceBar
    {
        @Inject(method = "extractBackground", at = @At("HEAD"))
        public void renderExperienceBar1(GuiGraphicsExtractor graphics, DeltaTracker delta, CallbackInfo ci)
        {
            if (ConfigSettings.CUSTOM_HOTBAR_LAYOUT.get())
            {   graphics.pose().pushMatrix();
                graphics.pose().translate(0, 4);
            }
        }

        @Inject(method = "extractBackground", at = @At("TAIL"))
        public void renderExperienceBar2(GuiGraphicsExtractor graphics, DeltaTracker delta, CallbackInfo ci)
        {
            if (ConfigSettings.CUSTOM_HOTBAR_LAYOUT.get())
            {   graphics.pose().popMatrix();
            }
        }
    }
}
