package com.momosoftworks.coldsweat.core.event;

import com.momosoftworks.coldsweat.common.entity.Chameleon;
import com.momosoftworks.coldsweat.core.init.ModEntities;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class RegisterAttributes
{
    public static void registerAttributes()
    {
        FabricDefaultAttributeRegistry.register(ModEntities.CHAMELEON.get(), Chameleon.createAttributes());
    }
}
