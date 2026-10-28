package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.api.event.vanilla.ItemBreakEvent;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class MixinArmorBreak
{
    @Inject(method = "applyDamage(ILnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    private void coldsweat$onItemBreak(int newDamage, ServerPlayer player, Consumer<Item> onBroken, CallbackInfo ci)
    {
        ModEvents.post(new ItemBreakEvent((ItemStack) (Object) this, player, onBroken));
    }
}
