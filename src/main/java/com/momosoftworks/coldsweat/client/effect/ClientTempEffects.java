package com.momosoftworks.coldsweat.client.effect;

import com.momosoftworks.coldsweat.api.temperature.effect.TempEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.TempEffectType;
import com.momosoftworks.coldsweat.api.temperature.effect.player.FreezeHeartsEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.player.FreezeShiverEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.player.FreezeVignetteEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.player.HeatBlurEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.player.HeatFogEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.player.HeatSwayEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.player.HeatVignetteEffect;
import com.momosoftworks.coldsweat.data.codec.util.IntegerBounds;

public final class ClientTempEffects
{
    private ClientTempEffects()
    {}

    public static TempEffect freezeHearts(TempEffectType<?> type, IntegerBounds bounds)
    {   return new FreezeHeartsEffect(type, bounds);
    }

    public static TempEffect freezeShiver(TempEffectType<?> type, IntegerBounds bounds)
    {   return new FreezeShiverEffect(type, bounds);
    }

    public static TempEffect freezeOverlay(TempEffectType<?> type, IntegerBounds bounds)
    {   return new FreezeVignetteEffect(type, bounds);
    }

    public static TempEffect heatBlur(TempEffectType<?> type, IntegerBounds bounds)
    {   return new HeatBlurEffect(type, bounds);
    }

    public static TempEffect heatFog(TempEffectType<?> type, IntegerBounds bounds)
    {   return new HeatFogEffect(type, bounds);
    }

    public static TempEffect heatSway(TempEffectType<?> type, IntegerBounds bounds)
    {   return new HeatSwayEffect(type, bounds);
    }

    public static TempEffect heatVignette(TempEffectType<?> type, IntegerBounds bounds)
    {   return new HeatVignetteEffect(type, bounds);
    }
}
