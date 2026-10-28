package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.item.alchemy.PotionBrewing;

public class RegisterBrewingRecipesEvent extends Event
{
    private final PotionBrewing.Builder builder;

    public RegisterBrewingRecipesEvent(PotionBrewing.Builder builder)
    {   this.builder = builder;
    }

    public PotionBrewing.Builder getBuilder()
    {   return builder;
    }
}