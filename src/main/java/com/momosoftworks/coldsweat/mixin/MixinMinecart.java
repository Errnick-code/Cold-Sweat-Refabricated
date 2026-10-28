package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.init.ModBlocks;
import com.momosoftworks.coldsweat.core.init.ModItems;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecart.class)
public class MixinMinecart
{
    @Inject(method = "getDropItem", at = @At("HEAD"), cancellable = true)
    private void coldsweat$insulatedDrop(CallbackInfoReturnable<Item> cir)
    {
        Minecart minecart = (Minecart) (Object) this;
        if (minecart.getDisplayBlockState().getBlock() == ModBlocks.MINECART_INSULATION.value())
        {   cir.setReturnValue(ModItems.INSULATED_MINECART.value());
        }
    }
}
