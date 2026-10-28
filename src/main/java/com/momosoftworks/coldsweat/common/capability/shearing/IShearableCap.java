package com.momosoftworks.coldsweat.common.capability.shearing;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public interface IShearableCap
{
    boolean isSheared();
    void setSheared(boolean sheared);

    int furGrowthCooldown();
    void setFurGrowthCooldown(int cooldown);

    CompoundTag serializeNBT(HolderLookup.Provider provider);
    void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt);
    int age();
    void setAge(int ticks);
}