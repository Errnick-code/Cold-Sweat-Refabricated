package com.momosoftworks.coldsweat.common.capability;

import com.momosoftworks.coldsweat.util.math.MappedCache;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

import java.util.function.Predicate;

public class CapabilityCache<C, K extends AttachmentTarget> extends MappedCache<K, C>
{
    protected final AttachmentType<C> attachment;

    public CapabilityCache(AttachmentType<C> attachment, Predicate<K> invalidator)
    {
        super(key -> key.getAttachedOrCreate(attachment), invalidator);
        this.attachment = attachment;
    }

    public CapabilityCache(AttachmentType<C> attachment)
    {
        super(key -> key.getAttachedOrCreate(attachment));
        this.attachment = attachment;
    }
}