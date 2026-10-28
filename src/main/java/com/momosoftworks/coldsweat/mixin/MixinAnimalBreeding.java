package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.BabyEntitySpawnEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Animal.class)
public class MixinAnimalBreeding
{
    @Inject(method = "spawnChildFromBreeding", at = @At("HEAD"), cancellable = true)
    private void coldsweat$onBreed(ServerLevel level, Animal partner, CallbackInfo ci)
    {
        Animal self = (Animal) (Object) this;
        BabyEntitySpawnEvent event = ModEvents.post(new BabyEntitySpawnEvent(self, partner));
        if (event.isCanceled())
        {   self.setAge(6000);
            partner.setAge(6000);
            self.resetLove();
            partner.resetLove();
            ci.cancel();
        }
    }
}
