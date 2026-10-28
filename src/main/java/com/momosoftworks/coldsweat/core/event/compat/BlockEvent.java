package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BlockEvent extends Event
{
    private final LevelAccessor level;
    private final BlockPos pos;
    private final BlockState state;

    public BlockEvent(LevelAccessor level, BlockPos pos, BlockState state)
    {   this.level = level;
        this.pos = pos;
        this.state = state;
    }

    public LevelAccessor getLevel()
    {   return level;
    }

    public BlockPos getPos()
    {   return pos;
    }

    public BlockState getState()
    {   return state;
    }

    public static class BreakEvent extends BlockEvent implements Cancellable
    {
        private final Player player;

        public BreakEvent(LevelAccessor level, BlockPos pos, BlockState state, Player player)
        {   super(level, pos, state);
            this.player = player;
        }

        public Player getPlayer()
        {   return player;
        }
    }

    public static class PlaceEvent extends BlockEvent implements Cancellable
    {
        private final Player player;

        public PlaceEvent(LevelAccessor level, BlockPos pos, BlockState state, Player player)
        {   super(level, pos, state);
            this.player = player;
        }

        public Player getPlayer()
        {   return player;
        }
    }
}