package com.momosoftworks.coldsweat.api.temperature.block_temp.compat;

import com.momosoftworks.coldsweat.api.temperature.block_temp.BlockTemp;
import com.momosoftworks.coldsweat.compat.create.CreateCompat;
import com.zurrtum.create.AllBlocks;
import com.zurrtum.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class CreateFluidTankTemp extends BlockTemp
{
    public CreateFluidTankTemp()
    {   super(AllBlocks.FLUID_TANK, AllBlocks.CREATIVE_FLUID_TANK);
    }

    @Override
    public double getTemperature(Level level, @Nullable LivingEntity entity, BlockState state, BlockPos pos, double distance)
    {
        FluidTankBlockEntity controller = CreateCompat.getTankController(level, pos);
        int heat = CreateCompat.getBoilerHeat(controller);
        if (heat > 0)
        {   return Math.min(3, heat / 4.0);
        }
        double fillFraction = CreateCompat.getFillFraction(controller, pos);
        return mapFluidBlockTemp(level, pos, blockTemp -> blockTemp.getTemperature(level, entity, state, pos, distance) / 2, 0.0) * fillFraction;
    }

    protected static <T> T mapFluidBlockTemp(Level level, BlockPos pos, Function<BlockTemp, T> consumer, T defaultVal)
    {
        return CreateCompat.mapFluidBlockTemp(CreateCompat.getTankFluid(level, pos), level, pos, consumer, defaultVal);
    }

    @Override
    public double getMinEffect(@Nullable LivingEntity entity, Level level, BlockPos pos, BlockState state)
    {   return mapFluidBlockTemp(level, pos, blockTemp -> blockTemp.getMinEffect(entity, level, pos, state), Double.NEGATIVE_INFINITY);
    }

    @Override
    public double getMaxEffect(@Nullable LivingEntity entity, Level level, BlockPos pos, BlockState state)
    {   return mapFluidBlockTemp(level, pos, blockTemp -> blockTemp.getMaxEffect(entity, level, pos, state), Double.POSITIVE_INFINITY);
    }

    @Override
    public double getMinTemp(LivingEntity entity, Level level, BlockPos pos, BlockState state)
    {   return mapFluidBlockTemp(level, pos, blockTemp -> blockTemp.getMinTemp(entity, level, pos, state), Double.NEGATIVE_INFINITY);
    }

    @Override
    public double getMaxTemp(@Nullable LivingEntity entity, Level level, BlockPos pos, BlockState state)
    {   return mapFluidBlockTemp(level, pos, blockTemp -> blockTemp.getMaxTemp(entity, level, pos, state), Double.POSITIVE_INFINITY);
    }

    @Override
    public double getRange(@Nullable LivingEntity entity, Level level, BlockPos pos, BlockState state)
    {   return mapFluidBlockTemp(level, pos, blockTemp -> blockTemp.getRange(entity, level, pos, state), 7.0);
    }

    @Override
    public boolean isLogarithmic(@Nullable LivingEntity entity, Level level, BlockPos pos, BlockState state)
    {   return mapFluidBlockTemp(level, pos, blockTemp -> blockTemp.isLogarithmic(entity, level, pos, state), false);
    }

    @Override
    public boolean fades(@Nullable LivingEntity entity, Level level, BlockPos pos, BlockState state)
    {   return mapFluidBlockTemp(level, pos, blockTemp -> blockTemp.fades(entity, level, pos, state), true);
    }
}
