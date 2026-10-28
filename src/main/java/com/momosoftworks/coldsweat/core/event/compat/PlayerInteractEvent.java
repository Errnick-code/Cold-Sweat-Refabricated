package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class PlayerInteractEvent extends Event implements Cancellable
{
    private final Player player;
    private final InteractionHand hand;
    private InteractionResult cancellationResult = InteractionResult.PASS;

    public PlayerInteractEvent(Player player, InteractionHand hand)
    {   this.player = player;
        this.hand = hand;
    }

    public Player getEntity()
    {   return player;
    }

    public InteractionHand getHand()
    {   return hand;
    }

    public ItemStack getItemStack()
    {   return player.getItemInHand(hand);
    }

    public InteractionResult getCancellationResult()
    {   return cancellationResult;
    }

    public void setCancellationResult(InteractionResult result)
    {   this.cancellationResult = result;
    }

    public static class RightClickItem extends PlayerInteractEvent
    {
        public RightClickItem(Player player, InteractionHand hand)
        {   super(player, hand);
        }
    }

    public static class LeftClickEmpty extends PlayerInteractEvent
    {
        public LeftClickEmpty(Player player, InteractionHand hand)
        {   super(player, hand);
        }
    }

    public static class EntityInteract extends PlayerInteractEvent
    {
        private final Entity target;

        public EntityInteract(Player player, InteractionHand hand, Entity target)
        {   super(player, hand);
            this.target = target;
        }

        public Entity getTarget()
        {   return target;
        }
    }

    public static class EntityInteractSpecific extends PlayerInteractEvent
    {
        private final Entity target;

        public EntityInteractSpecific(Player player, InteractionHand hand, Entity target)
        {   super(player, hand);
            this.target = target;
        }

        public Entity getTarget()
        {   return target;
        }
    }
}