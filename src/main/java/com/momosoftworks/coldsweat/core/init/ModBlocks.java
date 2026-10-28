package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.common.block.BoilerBlock;
import com.momosoftworks.coldsweat.common.block.HearthBottomBlock;
import com.momosoftworks.coldsweat.common.block.HearthTopBlock;
import com.momosoftworks.coldsweat.common.block.IceboxBlock;
import com.momosoftworks.coldsweat.common.block.MinecartInsulationBlock;
import com.momosoftworks.coldsweat.common.block.SewingTableBlock;
import com.momosoftworks.coldsweat.common.block.SlushLiquidBlock;
import com.momosoftworks.coldsweat.common.block.SmokestackBlock;
import com.momosoftworks.coldsweat.common.block.SoulStalkBlock;
import com.momosoftworks.coldsweat.common.block.ThermolithBlock;
import com.momosoftworks.coldsweat.common.fluid.SlushFluid;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks
{
    public static final Ref<Block> BOILER = block("boiler", BoilerBlock::getProperties, BoilerBlock::new);
    public static final Ref<Block> ICEBOX = block("icebox", IceboxBlock::getProperties, IceboxBlock::new);
    public static final Ref<Block> SEWING_TABLE = block("sewing_table", SewingTableBlock::getProperties, SewingTableBlock::new);
    public static final Ref<Block> MINECART_INSULATION = block("minecart_insulation", MinecartInsulationBlock::getProperties, MinecartInsulationBlock::new);
    public static final Ref<Block> HEARTH_BOTTOM = block("hearth_bottom", HearthBottomBlock::getProperties, HearthBottomBlock::new);
    public static final Ref<Block> HEARTH_TOP = block("hearth_top", HearthTopBlock::getProperties, HearthTopBlock::new);
    public static final Ref<Block> THERMOLITH = block("thermolith", ThermolithBlock::getProperties, ThermolithBlock::new);
    public static final Ref<Block> SOUL_STALK = block("soul_stalk", SoulStalkBlock::getProperties, SoulStalkBlock::new);
    public static final Ref<Block> SMOKESTACK = block("smokestack", SmokestackBlock::getProperties, SmokestackBlock::new);
    public static final Ref<Block> SLUSH = block("slush", SlushFluid::getBlockProperties,
                                                 properties -> new SlushLiquidBlock(() -> ModFluids.SLUSH.value(), properties));

    private static <T extends Block> Ref<T> block(String name, Supplier<BlockBehaviour.Properties> properties, Function<BlockBehaviour.Properties, T> factory)
    {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, ColdSweat.createKey(name));
        T block = factory.apply(properties.get().setId(key));
        return new Ref<>(Registry.register(BuiltInRegistries.BLOCK, key, block));
    }
}
