package com.momosoftworks.coldsweat.compat.create;

import com.momosoftworks.coldsweat.api.registry.BlockTempRegistry;
import com.momosoftworks.coldsweat.api.temperature.block_temp.BlockTemp;
import com.momosoftworks.coldsweat.util.math.CSMath;
import com.zurrtum.create.content.fluids.FluidTransportBehaviour;
import com.zurrtum.create.content.fluids.PipeConnection;
import com.zurrtum.create.content.fluids.pipes.EncasedPipeBlock;
import com.zurrtum.create.content.fluids.pipes.FluidPipeBlock;
import com.zurrtum.create.content.fluids.pipes.FluidPipeBlockEntity;
import com.zurrtum.create.content.fluids.pipes.GlassFluidPipeBlock;
import com.zurrtum.create.content.fluids.tank.FluidTankBlockEntity;
import com.zurrtum.create.infrastructure.fluids.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public final class CreateCompat
{
    private CreateCompat() {}

    public static boolean isFluidPipe(BlockState state)
    {
        return state.getBlock() instanceof FluidPipeBlock
            || state.getBlock() instanceof GlassFluidPipeBlock
            || state.getBlock() instanceof EncasedPipeBlock;
    }

    public static FluidStack getPipeFluid(Level level, BlockPos pos)
    {
        if (!(level.getBlockEntity(pos) instanceof FluidPipeBlockEntity pipe))
        {   return FluidStack.EMPTY;
        }
        FluidTransportBehaviour behaviour = pipe.getBehaviour(FluidTransportBehaviour.TYPE);
        if (behaviour == null || behaviour.interfaces == null)
        {   return FluidStack.EMPTY;
        }
        for (PipeConnection connection : behaviour.interfaces.values())
        {
            if (connection == null) continue;
            FluidStack fluid = connection.getProvidedFluid();
            if (fluid != null && !fluid.isEmpty())
            {   return fluid;
            }
        }
        return FluidStack.EMPTY;
    }

    @Nullable
    public static FluidTankBlockEntity getTankController(Level level, BlockPos pos)
    {
        if (!(level.getBlockEntity(pos) instanceof FluidTankBlockEntity tank))
        {   return null;
        }
        return tank.getControllerBE();
    }

    public static FluidStack getTankFluid(Level level, BlockPos pos)
    {
        FluidTankBlockEntity controller = getTankController(level, pos);
        if (controller == null) return FluidStack.EMPTY;
        FluidStack fluid = controller.getFluid(0);
        if (fluid == null || fluid.isEmpty() || getFillFraction(controller, pos) <= 0)
        {   return FluidStack.EMPTY;
        }
        return fluid;
    }

    public static double getFillFraction(Level level, BlockPos pos)
    {
        FluidTankBlockEntity controller = getTankController(level, pos);
        return controller == null ? 0 : getFillFraction(controller, pos);
    }

    public static double getFillFraction(FluidTankBlockEntity controller, BlockPos pos)
    {
        FluidStack fluid = controller.getFluid(0);
        if (fluid == null || fluid.isEmpty()) return 0;
        long capacity = (long) controller.getTotalTankSize() * FluidTankBlockEntity.getCapacityMultiplier();
        if (capacity <= 0) return 0;
        double fluidHeight = (double) fluid.getAmount() / capacity * controller.getHeight();
        double blockMinY = pos.getY() - controller.getBlockPos().getY();
        return CSMath.clamp(fluidHeight - blockMinY, 0, 1);
    }

    public static int getBoilerHeat(@Nullable FluidTankBlockEntity controller)
    {
        if (controller == null || !controller.isController() || controller.boiler == null) return 0;
        return controller.boiler.passiveHeat ? 1 : controller.boiler.getTheoreticalHeatLevel();
    }

    public static BlockState getFluidBlockState(FluidStack fluid)
    {
        return fluid.getFluid().defaultFluidState().createLegacyBlock();
    }

    public static <T> T mapFluidBlockTemp(FluidStack fluid, Level level, BlockPos pos, Function<BlockTemp, T> consumer, T defaultVal)
    {
        if (fluid == null || fluid.isEmpty()) return defaultVal;
        BlockState fluidState = getFluidBlockState(fluid);
        return BlockTempRegistry.getFirstBlockTempFor(fluidState, level, pos).map(consumer).orElse(defaultVal);
    }
}
