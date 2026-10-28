package com.momosoftworks.coldsweat.common.fluid;

import com.momosoftworks.coldsweat.core.init.ModBlocks;
import com.momosoftworks.coldsweat.core.init.ModFluids;
import com.momosoftworks.coldsweat.core.init.ModItems;
import com.momosoftworks.coldsweat.util.registries.ModGameRules;
import com.momosoftworks.coldsweat.util.world.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;

public abstract class SlushFluid extends FlowingFluid
{
    public static BlockBehaviour.Properties getBlockProperties()
    {
        return BlockBehaviour.Properties.of()
                                       .mapColor(MapColor.ICE)
                                       .replaceable()
                                       .noCollision()
                                       .randomTicks()
                                       .strength(100.0F)
                                       .pushReaction(PushReaction.DESTROY)
                                       .noLootTable()
                                       .liquid()
                                       .sound(SoundType.EMPTY);
    }

    @Override
    public Fluid getFlowing()
    {   return ModFluids.FLOWING_SLUSH.value();
    }

    @Override
    public Fluid getSource()
    {   return ModFluids.SLUSH.value();
    }

    @Override
    public Item getBucket()
    {   return ModItems.SLUSH_BUCKET.value();
    }

    @Override
    public int getSlopeFindDistance(LevelReader level)
    {   return 2;
    }

    @Override
    public int getDropOff(LevelReader level)
    {   return 2;
    }

    @Override
    public int getTickDelay(LevelReader level)
    {   return 30;
    }

    @Override
    protected float getExplosionResistance()
    {   return 100.0F;
    }

    @Override
    public BlockState createLegacyBlock(FluidState state)
    {   return ModBlocks.SLUSH.value().defaultBlockState();
    }

    @Override
    public boolean isSame(Fluid fluid)
    {   return fluid == ModFluids.SLUSH.value() || fluid == ModFluids.FLOWING_SLUSH.value();
    }

    @Override
    public boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction)
    {   return direction == Direction.DOWN && !fluid.is(FluidTags.WATER);
    }

    @Override
    protected boolean canConvertToSource(net.minecraft.server.level.ServerLevel level)
    {   return level.getGameRules().get(ModGameRules.RULE_SLUSH_SOURCE_CONVERSION.value());
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState blockState)
    {}

    @Override
    protected void spreadTo(LevelAccessor level, BlockPos pos, BlockState blockState, Direction direction, FluidState fluidState)
    {
        FluidState targetFluidState = level.getFluidState(pos);

        if (targetFluidState.is(FluidTags.WATER))
        {
            level.setBlock(pos, Blocks.SNOW_BLOCK.defaultBlockState(), 3);
            fizz(level, pos);
            return;
        }
        super.spreadTo(level, pos, blockState, direction, fluidState);
    }

    public static void fizz(LevelAccessor ilevel, BlockPos pos)
    {
        if (ilevel instanceof Level level)
        {
            Vec3 centerPos = pos.getCenter();
            BlockParticleOption particleData = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SNOW_BLOCK.defaultBlockState());
            WorldHelper.spawnParticleBatch(level, particleData, centerPos.x, centerPos.y, centerPos.z, 0.6, 0.6, 0.6, 15, 0.1);
            level.playSound(null, pos, SoundEvents.SNOW_BREAK, SoundSource.BLOCKS);
        }
    }

    public static class Flowing extends SlushFluid
    {
        public Flowing()
        {
            registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder)
        {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state)
        {   return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state)
        {   return false;
        }
    }

    public static class Source extends SlushFluid
    {
        @Override
        public int getAmount(FluidState state)
        {   return 8;
        }

        @Override
        public boolean isSource(FluidState state)
        {   return true;
        }
    }
}