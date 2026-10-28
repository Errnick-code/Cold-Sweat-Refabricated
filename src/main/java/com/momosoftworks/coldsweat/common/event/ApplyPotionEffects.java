package com.momosoftworks.coldsweat.common.event;

import com.momosoftworks.coldsweat.common.data.PersistentData;

import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.core.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import com.momosoftworks.coldsweat.core.event.SubscribeEvent;
import com.momosoftworks.coldsweat.core.event.compat.EntityJoinLevelEvent;
import com.momosoftworks.coldsweat.core.event.compat.LivingEntityUseItemEvent;
public class ApplyPotionEffects
{
    @SubscribeEvent
    public static void onSpawn(EntityJoinLevelEvent event)
    {
        if (!event.getLevel().isClientSide() && event.getEntity() instanceof Player
        && ConfigSettings.GRACE_ENABLED.get() && !PersistentData.get(event.getEntity()).getBoolean("GivenGracePeriod").orElse(false))
        {
            PersistentData.get(event.getEntity()).putBoolean("GivenGracePeriod", true);
            ((Player) event.getEntity()).addEffect(new MobEffectInstance(ModEffects.GRACE.get(), ConfigSettings.GRACE_LENGTH.get(), 0, false, false, true));
        }
    }

    @SubscribeEvent
    public static void onGoldenAppleEaten(LivingEntityUseItemEvent.Finish event)
    {
        ItemStack item = event.getItem();
        if (item.is(Items.ENCHANTED_GOLDEN_APPLE))
        {   event.getEntity().addEffect(new MobEffectInstance(ModEffects.ICE_RESISTANCE.get(), 6000, 0));
        }
    }
}
