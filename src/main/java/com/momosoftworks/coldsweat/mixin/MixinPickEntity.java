package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.api.event.vanilla.EntityPickEvent;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerGamePacketListenerImpl.class)
public class MixinPickEntity
{
    @Redirect(method = "handlePickItemFromEntity",
              at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getPickResult()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack getPickResult(Entity entity)
    {
        EntityPickEvent event = new EntityPickEvent(entity, entity.getPickResult());
        ModEvents.post(event);
        return event.getStack();
    }
}
