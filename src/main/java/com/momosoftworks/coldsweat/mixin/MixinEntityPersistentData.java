package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.common.data.PersistentDataHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntityPersistentData implements PersistentDataHolder
{
    @Unique
    private CompoundTag coldsweat$persistentData;

    @Override
    public CompoundTag coldsweat$getData()
    {
        if (this.coldsweat$persistentData == null)
        {   this.coldsweat$persistentData = new CompoundTag();
        }
        return this.coldsweat$persistentData;
    }

    @Inject(method = "saveWithoutId", at = @At("RETURN"))
    private void coldsweat$save(ValueOutput output, CallbackInfo ci)
    {
        if (this.coldsweat$persistentData != null && !this.coldsweat$persistentData.isEmpty())
        {   output.store("ColdSweatData", CompoundTag.CODEC, this.coldsweat$persistentData.copy());
        }
    }

    @Inject(method = "load", at = @At("RETURN"))
    private void coldsweat$load(ValueInput input, CallbackInfo ci)
    {
        input.read("ColdSweatData", CompoundTag.CODEC)
             .ifPresent(tag -> this.coldsweat$persistentData = tag.copy());
    }
}
