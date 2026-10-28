package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.client.event.RegisterModels;
import com.momosoftworks.coldsweat.core.init.ModItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HumanoidArmorLayer.class)
public class MixinHumanoidArmorLayer
{
    @Inject(method = "getArmorModel(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/client/model/HumanoidModel;",
            at = @At("RETURN"), cancellable = true)
    private void coldsweat$getArmorModel(HumanoidRenderState state, EquipmentSlot slot, CallbackInfoReturnable<HumanoidModel> cir)
    {
        ItemStack stack = switch (slot)
        {
            case HEAD -> state.headEquipment;
            case CHEST -> state.chestEquipment;
            case LEGS -> state.legsEquipment;
            case FEET -> state.feetEquipment;
            default -> ItemStack.EMPTY;
        };
        if (stack.isEmpty()) return;

        HumanoidModel<?> model = null;
        if (stack.is(ModItems.GOAT_FUR_HELMET.value()) || stack.is(ModItems.GOAT_FUR_CHESTPLATE.value())
            || stack.is(ModItems.GOAT_FUR_LEGGINGS.value()) || stack.is(ModItems.GOAT_FUR_BOOTS.value()))
        {   model = RegisterModels.getGoatArmorModel(slot);
        }
        else if (stack.is(ModItems.HOGLIN_HELMET.value()) || stack.is(ModItems.HOGLIN_CHESTPLATE.value())
                 || stack.is(ModItems.HOGLIN_LEGGINGS.value()) || stack.is(ModItems.HOGLIN_BOOTS.value()))
        {   model = RegisterModels.getHoglinArmorModel(slot);
        }
        if (model != null)
        {   cir.setReturnValue(model);
        }
    }
}
