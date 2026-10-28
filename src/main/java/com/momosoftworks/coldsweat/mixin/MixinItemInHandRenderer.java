package com.momosoftworks.coldsweat.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.momosoftworks.coldsweat.client.event.RenderLampHand;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.core.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class MixinItemInHandRenderer
{
    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void coldsweat$renderLampHand(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand,
                                          float swingProgress, ItemStack stack, float equipProgress, PoseStack poseStack,
                                          SubmitNodeCollector collector, int light, CallbackInfo ci)
    {
        if (stack.getItem() != ModItems.SOULSPRING_LAMP.value() || !ConfigSettings.POSE_SOULSPRING_LAMP.get()) return;

        RenderLampHand.renderFirstPerson(poseStack, collector, light, Minecraft.getInstance().player, stack, hand, swingProgress, equipProgress);
        ci.cancel();
    }
}
