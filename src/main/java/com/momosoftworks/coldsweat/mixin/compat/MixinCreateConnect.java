package com.momosoftworks.coldsweat.mixin.compat;

import com.momosoftworks.coldsweat.common.block.SmokestackBlock;
import com.zurrtum.create.content.fluids.pipes.FluidPipeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndLightGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidPipeBlock.class)
public class MixinCreateConnect
{
    @Inject(method = "canConnectTo", at = @At("HEAD"), cancellable = true, remap = false)
    private static void shouldPipesConnectTo(BlockAndLightGetter world, BlockPos pos, BlockState neighborState, Direction direction, CallbackInfoReturnable<Boolean> cir)
    {
        if (neighborState.getBlock() instanceof SmokestackBlock)
        {   cir.setReturnValue(true);
        }
    }
}
