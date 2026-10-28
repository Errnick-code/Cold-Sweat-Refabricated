package com.momosoftworks.coldsweat.mixin;

import com.momosoftworks.coldsweat.api.event.vanilla.BlockStateChangedEvent;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelChunk.class)
public abstract class MixinBlockUpdate
{
    @Inject(method = "setBlockState", at = @At("HEAD"))
    private void coldsweat$onBlockUpdate(BlockPos pos, BlockState newState, int flags, CallbackInfoReturnable<BlockState> cir)
    {
        LevelChunk chunk = (LevelChunk) (Object) this;
        Level level = chunk.getLevel();
        BlockState oldState = chunk.getBlockState(pos);

        if (oldState.equals(newState)) return;

        if (level instanceof ServerLevel serverLevel)
        {   serverLevel.getServer().execute(() -> ModEvents.post(new BlockStateChangedEvent(pos, serverLevel, oldState, newState)));
        }
        else
        {   ModEvents.post(new BlockStateChangedEvent(pos, level, oldState, newState));
        }
    }
}
