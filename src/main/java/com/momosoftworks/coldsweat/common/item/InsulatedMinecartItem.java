package com.momosoftworks.coldsweat.common.item;

import com.momosoftworks.coldsweat.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class InsulatedMinecartItem extends Item
{
    public InsulatedMinecartItem(Properties itemProperties)
    {
        super(itemProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.is(BlockTags.RAILS))
        {
            ItemStack itemstack = context.getItemInHand();
            if (!level.isClientSide())
            {
                Minecart minecart = new Minecart(net.minecraft.world.entity.EntityType.MINECART, level);
                minecart.setPos(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D);
                if (itemstack.has(DataComponents.CUSTOM_NAME))
                {   minecart.setCustomName(itemstack.getHoverName());
                }
                minecart.setCustomDisplayBlockState(java.util.Optional.of(ModBlocks.MINECART_INSULATION.value().defaultBlockState()));
                minecart.setDisplayOffset(5);
                level.addFreshEntity(minecart);
            }
            itemstack.shrink(1);
            return InteractionResult.SUCCESS;
        }
        else return InteractionResult.PASS;
    }
}
