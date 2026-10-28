package com.momosoftworks.coldsweat.core.event.compat.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class RenderHandEvent extends Event implements Cancellable
{
    private final InteractionHand hand;
    private final ItemStack itemStack;
    private final PoseStack poseStack;
    private final SubmitNodeCollector submitNodeCollector;
    private final int packedLight;
    private final float equipProgress;
    private final float swingProgress;

    public RenderHandEvent(InteractionHand hand, ItemStack itemStack, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                           int packedLight, float equipProgress, float swingProgress)
    {
        this.hand = hand;
        this.itemStack = itemStack;
        this.poseStack = poseStack;
        this.submitNodeCollector = submitNodeCollector;
        this.packedLight = packedLight;
        this.equipProgress = equipProgress;
        this.swingProgress = swingProgress;
    }

    public InteractionHand getHand()
    {   return hand;
    }

    public ItemStack getItemStack()
    {   return itemStack;
    }

    public PoseStack getPoseStack()
    {   return poseStack;
    }

    public SubmitNodeCollector getSubmitNodeCollector()
    {   return submitNodeCollector;
    }

    public int getPackedLight()
    {   return packedLight;
    }

    public float getEquipProgress()
    {   return equipProgress;
    }

    public float getSwingProgress()
    {   return swingProgress;
    }
}
