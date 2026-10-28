package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.common.blockentity.BoilerBlockEntity;
import com.momosoftworks.coldsweat.common.blockentity.HearthBlockEntity;
import com.momosoftworks.coldsweat.common.blockentity.IceboxBlockEntity;
import com.momosoftworks.coldsweat.common.blockentity.ThermolithBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities
{
    public static final Ref<BlockEntityType<BoilerBlockEntity>> BOILER = be("boiler", BoilerBlockEntity::new, ModBlocks.BOILER.value());
    public static final Ref<BlockEntityType<IceboxBlockEntity>> ICEBOX = be("icebox", IceboxBlockEntity::new, ModBlocks.ICEBOX.value());
    public static final Ref<BlockEntityType<HearthBlockEntity>> HEARTH = be("hearth", HearthBlockEntity::new, ModBlocks.HEARTH_BOTTOM.value());
    public static final Ref<BlockEntityType<ThermolithBlockEntity>> THERMOLITH = be("thermolith", ThermolithBlockEntity::new, ModBlocks.THERMOLITH.value());

    private static <T extends BlockEntity> Ref<BlockEntityType<T>> be(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block block)
    {
        return new Ref<>(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ColdSweat.createKey(name),
                                            FabricBlockEntityTypeBuilder.create(factory, block).build()));
    }
}
