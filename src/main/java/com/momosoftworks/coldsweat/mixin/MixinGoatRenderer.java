package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.common.capability.handler.ShearableFurManager;
import com.momosoftworks.coldsweat.common.capability.shearing.IShearableCap;
import com.momosoftworks.coldsweat.mixin_api.GoatRenderStateAccess;
import net.minecraft.client.renderer.entity.GoatRenderer;
import net.minecraft.client.renderer.entity.state.GoatRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.goat.Goat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GoatRenderer.class)
public class MixinGoatRenderer
{
    private static final Identifier SHEARED_GOAT_TEXTURE = Identifier.withDefaultNamespace("textures/entity/goat/goat_shaven.png");

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/animal/goat/Goat;Lnet/minecraft/client/renderer/entity/state/GoatRenderState;F)V",
            at = @At("TAIL"))
    private void coldsweat$extractSheared(Goat goat, GoatRenderState state, float partialTick, CallbackInfo ci)
    {
        ((GoatRenderStateAccess) state).coldsweat$setSheared(ShearableFurManager.getFurCap(goat).map(IShearableCap::isSheared).orElse(false));
    }

    @Inject(method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/GoatRenderState;)Lnet/minecraft/resources/Identifier;",
            at = @At("HEAD"), cancellable = true)
    private void getTextureLocation(GoatRenderState state, CallbackInfoReturnable<Identifier> cir)
    {
        if (((GoatRenderStateAccess) state).coldsweat$isSheared())
        {   cir.setReturnValue(SHEARED_GOAT_TEXTURE);
        }
    }
}
