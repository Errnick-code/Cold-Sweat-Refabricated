package com.momosoftworks.coldsweat.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface MixinGameRendererAccessor
{
    @Invoker("setPostEffect")
    void coldsweat$setPostEffect(Identifier effect);
}
