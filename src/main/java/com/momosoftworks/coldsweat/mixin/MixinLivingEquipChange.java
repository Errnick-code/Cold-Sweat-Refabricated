package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.LivingEquipmentChangeEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEquipChange
{
    @Inject(method = "onEquipItem", at = @At("HEAD"))
    private void coldsweat$onEquipItem(EquipmentSlot slot, ItemStack from, ItemStack to, CallbackInfo ci)
    {
        ModEvents.post(new LivingEquipmentChangeEvent((LivingEntity) (Object) this, from, to));
    }
}
