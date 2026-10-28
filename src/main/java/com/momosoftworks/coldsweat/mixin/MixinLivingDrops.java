package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.core.event.DropsCapture;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.LivingDropsEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(LivingEntity.class)
public class MixinLivingDrops
{
    @Inject(method = "dropAllDeathLoot", at = @At("HEAD"))
    private void coldsweat$startCapturingDrops(ServerLevel level, DamageSource source, CallbackInfo ci)
    {
        DropsCapture.start();
    }

    @Inject(method = "dropAllDeathLoot", at = @At("RETURN"))
    private void coldsweat$onDrops(ServerLevel level, DamageSource source, CallbackInfo ci)
    {
        List<ItemEntity> captured = DropsCapture.stop();
        if (captured == null || captured.isEmpty()) return;

        List<ItemEntity> drops = new ArrayList<>(captured);
        LivingDropsEvent event = ModEvents.post(new LivingDropsEvent((LivingEntity) (Object) this, drops));

        for (ItemEntity drop : captured)
        {
            if (event.isCanceled() || !drops.contains(drop))
            {   drop.discard();
            }
        }
    }
}
