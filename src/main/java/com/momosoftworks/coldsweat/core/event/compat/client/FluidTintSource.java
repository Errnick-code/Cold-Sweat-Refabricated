package com.momosoftworks.coldsweat.core.event.compat.client;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface FluidTintSource extends BlockTintSource
{
    int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos);
}
