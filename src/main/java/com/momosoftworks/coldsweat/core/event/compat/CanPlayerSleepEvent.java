package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class CanPlayerSleepEvent extends Event implements Cancellable
{
    private final Player player;
    private final BlockPos pos;
    private Player.BedSleepingProblem problem;

    public CanPlayerSleepEvent(Player player, BlockPos pos, Player.BedSleepingProblem problem)
    {   this.player = player;
        this.pos = pos;
        this.problem = problem;
    }

    public Player getEntity()
    {   return player;
    }

    public BlockPos getPos()
    {   return pos;
    }

    public Player.BedSleepingProblem getProblem()
    {   return problem;
    }

    public void setProblem(Player.BedSleepingProblem problem)
    {   this.problem = problem;
    }
}