package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.common.entity.Chameleon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities
{
    public static final Ref<EntityType<Chameleon>> CHAMELEON = new Ref<>(Registry.register(BuiltInRegistries.ENTITY_TYPE, ColdSweat.createKey("chameleon"),
            EntityType.Builder.of(Chameleon::new, MobCategory.CREATURE)
                              .sized(0.75f, 0.65f)
                              .eyeHeight(0.35f)
                              .build(ResourceKey.create(Registries.ENTITY_TYPE, ColdSweat.createKey("chameleon")))));
}
