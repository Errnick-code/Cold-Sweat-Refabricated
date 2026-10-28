package com.momosoftworks.coldsweat.common.capability;

import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.world.entity.Entity;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class SidedCapabilityCache<C, K extends AttachmentTarget> extends CapabilityCache<C, K>
{
    protected final CapabilityCache<C, K> clientCache;

    public SidedCapabilityCache(AttachmentType<C> attachment, Predicate<K> invalidator)
    {
        super(attachment, invalidator);
        this.clientCache = new CapabilityCache<>(attachment, invalidator);
    }

    public SidedCapabilityCache(AttachmentType<C> attachment)
    {
        super(attachment);
        this.clientCache = new CapabilityCache<>(attachment);
    }

    private static boolean isClient(Object key)
    {   return key instanceof Entity entity && entity.level().isClientSide();
    }

    @Override
    public C get(K key)
    {   return isClient(key) ? clientCache.get(key) : super.get(key);
    }

    @Override
    public void remove(K key)
    {
        if (isClient(key))
        {   clientCache.remove(key);
        }
        else super.remove(key);
    }

    public void clearClient()
    {   clientCache.clear();
    }

    public void clearServer()
    {   super.clear();
    }

    @Override
    public void clear()
    {
        super.clear();
        clientCache.clear();
    }

    @Override
    public void ifPresent(K key, Consumer<C> consumer)
    {
        if (isClient(key))
        {   clientCache.ifPresent(key, consumer);
        }
        else super.ifPresent(key, consumer);
    }
}
