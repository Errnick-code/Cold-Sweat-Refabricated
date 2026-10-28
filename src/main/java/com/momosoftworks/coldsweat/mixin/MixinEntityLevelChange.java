package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.EntityLeaveLevelEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntityLevelChange
{
    @Inject(method = "setLevel", at = @At("HEAD"))
    private void coldsweat$onLevelChange(Level level, CallbackInfo ci)
    {
        Entity entity = (Entity) (Object) this;
        Level current = entity.level();
        if (current != null && current != level)
        {   ModEvents.post(new EntityLeaveLevelEvent(entity, current));
        }
    }
}
