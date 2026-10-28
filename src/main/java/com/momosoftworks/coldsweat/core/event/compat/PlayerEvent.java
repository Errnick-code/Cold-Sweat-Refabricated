package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public abstract class PlayerEvent extends Event
{
    private final Player player;

    public PlayerEvent(Player player)
    {   this.player = player;
    }

    public Player getEntity()
    {   return player;
    }

    public static class Clone extends PlayerEvent
    {
        private final Player original;
        private final boolean wasDeath;

        public Clone(Player player, Player original, boolean wasDeath)
        {   super(player);
            this.original = original;
            this.wasDeath = wasDeath;
        }

        public Player getOriginal()
        {   return original;
        }

        public boolean isWasDeath()
        {   return wasDeath;
        }
    }

    public static class LoggingIn extends PlayerEvent
    {
        public LoggingIn(Player player)
        {   super(player);
        }
    }

    public static class LoggingOut extends PlayerEvent
    {
        public LoggingOut(Player player)
        {   super(player);
        }
    }

    public static class StartTracking extends PlayerEvent
    {
        private final Entity target;

        public StartTracking(Player player, Entity target)
        {   super(player);
            this.target = target;
        }

        public Entity getTarget()
        {   return target;
        }
    }

    public static class StopTracking extends PlayerEvent
    {
        private final Entity target;

        public StopTracking(Player player, Entity target)
        {   super(player);
            this.target = target;
        }

        public Entity getTarget()
        {   return target;
        }
    }

    public static class PlayerLoggedInEvent extends PlayerEvent
    {
        public PlayerLoggedInEvent(Player player)
        {   super(player);
        }
    }

    public static class PlayerChangedDimensionEvent extends PlayerEvent
    {
        public PlayerChangedDimensionEvent(Player player)
        {   super(player);
        }
    }

    public static class BreakSpeed extends PlayerEvent implements Cancellable
    {
        private final BlockState state;
        private float newSpeed;

        public BreakSpeed(Player player, BlockState state, float newSpeed)
        {   super(player);
            this.state = state;
            this.newSpeed = newSpeed;
        }

        public BlockState getState()
        {   return state;
        }

        public float getNewSpeed()
        {   return newSpeed;
        }

        public void setNewSpeed(float newSpeed)
        {   this.newSpeed = newSpeed;
        }
    }

    public static class HarvestCheck extends PlayerEvent
    {
        private final BlockState targetBlock;
        private boolean canHarvest;

        public HarvestCheck(Player player, BlockState targetBlock, boolean canHarvest)
        {   super(player);
            this.targetBlock = targetBlock;
            this.canHarvest = canHarvest;
        }

        public BlockState getTargetBlock()
        {   return targetBlock;
        }

        public boolean getCanHarvest()
        {   return canHarvest;
        }

        public void setCanHarvest(boolean canHarvest)
        {   this.canHarvest = canHarvest;
        }
    }
}