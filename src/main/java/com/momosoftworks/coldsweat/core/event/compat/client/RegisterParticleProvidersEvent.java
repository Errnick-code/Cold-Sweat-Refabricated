package com.momosoftworks.coldsweat.core.event.compat.client;

import com.momosoftworks.coldsweat.core.event.Event;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteSet;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.Function;

public class RegisterParticleProvidersEvent extends Event
{
    public <T extends ParticleOptions> void registerSpriteSet(ParticleType<T> type, Function<SpriteSet, ParticleProvider<T>> factory)
    {
        ParticleProviderRegistry.getInstance().register(type, (FabricSpriteSet spriteSet) -> factory.apply(spriteSet));
    }
}
