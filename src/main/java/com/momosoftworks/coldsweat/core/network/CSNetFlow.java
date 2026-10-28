package com.momosoftworks.coldsweat.core.network;

/**
 * Direction information for an in-flight payload, mirroring NeoForge's {@code PayloadFlow}.
 */
public record CSNetFlow(boolean serverbound)
{
    public static final CSNetFlow SERVERBOUND = new CSNetFlow(true);
    public static final CSNetFlow CLIENTBOUND = new CSNetFlow(false);

    public boolean isServerbound()
    {   return this.serverbound;
    }

    public boolean isClientbound()
    {   return !this.serverbound;
    }
}