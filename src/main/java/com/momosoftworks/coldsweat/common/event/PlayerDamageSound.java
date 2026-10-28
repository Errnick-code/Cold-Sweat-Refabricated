package com.momosoftworks.coldsweat.common.event;

import com.momosoftworks.coldsweat.util.registries.ModDamageSources;
import com.momosoftworks.coldsweat.util.world.WorldHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import com.momosoftworks.coldsweat.core.event.SubscribeEvent;
import com.momosoftworks.coldsweat.core.event.compat.LivingIncomingDamageEvent;
public class PlayerDamageSound
{
    @SubscribeEvent
    public static void onPlayerHurt(LivingIncomingDamageEvent event)
    {
        if (ModDamageSources.isFreezing(event.getSource()))
        {
            if (event.getEntity() instanceof Player player && !event.getEntity().level().isClientSide())
            {   WorldHelper.playEntitySound(SoundEvents.PLAYER_HURT_FREEZE, player, player.getSoundSource(), 2f, player.getVoicePitch());
            }
        }
    }
}
