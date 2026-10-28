package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.api.temperature.effect.TempEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.TempEffectType;
import com.momosoftworks.coldsweat.api.temperature.effect.entity.DecreaseDropsEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.entity.PreventBreedingEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.player.FreezeHealingEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.player.FreezeKnockbackEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.player.FreezeMineSpeedEffect;
import com.momosoftworks.coldsweat.api.temperature.effect.player.FreezeMoveSpeedEffect;
import com.momosoftworks.coldsweat.client.effect.ClientTempEffects;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class ModTempEffects
{
    public static final ResourceKey<Registry<TempEffectType<?>>> REGISTRY_KEY =
            ResourceKey.createRegistryKey(ColdSweat.createKey("temp_effect"));
    public static final Registry<TempEffectType<?>> REGISTRY = new MappedRegistry<>(REGISTRY_KEY, Lifecycle.stable());

    public static final Ref<TempEffectType<?>> FREEZE_HEALING = effect("freeze_healing", new TempEffectType<>(FreezeHealingEffect::new));
    public static final Ref<TempEffectType<?>> FREEZE_HEARTS = effect("freeze_hearts", TempEffect.Side.CLIENT, ClientTempEffects::freezeHearts);
    public static final Ref<TempEffectType<?>> FREEZE_KNOCKBACK = effect("freeze_knockback", new TempEffectType<>(FreezeKnockbackEffect::new));
    public static final Ref<TempEffectType<?>> FREEZE_MINING_SPEED = effect("freeze_mining_speed", new TempEffectType<>(FreezeMineSpeedEffect::new));
    public static final Ref<TempEffectType<?>> FREEZE_MOVEMENT_SPEED = effect("freeze_movement_speed", new TempEffectType<>(FreezeMoveSpeedEffect::new));
    public static final Ref<TempEffectType<?>> FREEZE_SHIVER = effect("freeze_shiver", TempEffect.Side.CLIENT, ClientTempEffects::freezeShiver);
    public static final Ref<TempEffectType<?>> FREEZE_OVERLAY = effect("freeze_overlay", TempEffect.Side.CLIENT, ClientTempEffects::freezeOverlay);
    public static final Ref<TempEffectType<?>> HEAT_BLUR = effect("heat_blur", TempEffect.Side.CLIENT, ClientTempEffects::heatBlur);
    public static final Ref<TempEffectType<?>> HEAT_FOG = effect("heat_fog", TempEffect.Side.CLIENT, ClientTempEffects::heatFog);
    public static final Ref<TempEffectType<?>> HEAT_SWAY = effect("heat_sway", TempEffect.Side.CLIENT, ClientTempEffects::heatSway);
    public static final Ref<TempEffectType<?>> HEAT_VIGNETTE = effect("heat_vignette", TempEffect.Side.CLIENT, ClientTempEffects::heatVignette);
    public static final Ref<TempEffectType<?>> PREVENT_BREEDING = effect("prevent_breeding", new TempEffectType<>(PreventBreedingEffect::new));
    public static final Ref<TempEffectType<?>> DECREASE_DROPS = effect("decrease_drops", new TempEffectType<>(DecreaseDropsEffect::new));

    private static Ref<TempEffectType<?>> effect(String name, TempEffectType<?> type)
    {   return new Ref<>(Registry.register(REGISTRY, ColdSweat.createKey(name), type));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Ref<TempEffectType<?>> effect(String name, TempEffect.Side side, TempEffectType.Constructor constructor)
    {   return new Ref<>(Registry.register(REGISTRY, ColdSweat.createKey(name), new TempEffectType(side, constructor)));
    }
}
