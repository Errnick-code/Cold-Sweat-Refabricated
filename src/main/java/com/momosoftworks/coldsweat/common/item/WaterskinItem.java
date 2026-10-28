package com.momosoftworks.coldsweat.common.item;

import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.core.init.ModItemComponents;
import com.momosoftworks.coldsweat.core.init.ModItems;
import com.momosoftworks.coldsweat.core.init.ModSounds;
import com.momosoftworks.coldsweat.compat.CompatManager;
import com.momosoftworks.coldsweat.util.math.CSMath;
import com.momosoftworks.coldsweat.util.world.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Optional;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

public class WaterskinItem extends Item
{
    public static final int FLUID_VALUE_MB = 250;

    public WaterskinItem(Properties properties)
    {
        super(properties.stacksTo(16));
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();

        if (player == null)
        {   WorldHelper.dropItem(level, pos, getFilledItem(context.getItemInHand(), level, pos));
            return super.useOn(context);
        }

        // Drain water from cauldron
        if (player.getAbilities().mayBuild && state.getBlock() == Blocks.WATER_CAULDRON
        && state.getValue(BlockStateProperties.LEVEL_CAULDRON) > 0)
        {
            if (!player.isCreative())
            {   LayeredCauldronBlock.lowerFillLevel(state, level, pos);
            }
            WaterskinItem.handleFillWaterskin(player, context.getItemInHand(), context.getHand(), pos);
            WorldHelper.spawnParticleBatch(level, ParticleTypes.SPLASH, pos.getX() + 0.5, pos.getY() + 0.65, pos.getZ() + 0.5, 0.5, 0.5, 0.5, 10, 0);

            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand)
    {
        InteractionResult ar = super.use(level, player, hand);
        ItemStack itemstack = player.getItemInHand(hand);

        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        BlockPos hitPos = blockhitresult.getBlockPos();
        BlockState lookingAt = level.getBlockState(hitPos);

        if (blockhitresult.getType() != HitResult.Type.BLOCK)
        {   return InteractionResult.PASS;
        }
        else
        {
            if (lookingAt.getFluidState().isSource() && lookingAt.getFluidState().getType().isSame(Fluids.WATER))
            {
                WaterskinItem.handleFillWaterskin(player, itemstack, hand, hitPos);
                WorldHelper.spawnParticleBatch(level, ParticleTypes.SPLASH, hitPos.getX() + 0.5, hitPos.getY() + 1, hitPos.getZ() + 0.5, 0.5, 0.5, 0.5, 10, 0);
            }
            return ar;
        }
    }

    public static ItemStack getFilledItem(ItemStack stack, Level level, BlockPos pos)
    {
        ItemStack filledWaterskin = ModItems.FILLED_WATERSKIN.value().getDefaultInstance();
        // copy NBT to new item
        filledWaterskin.applyComponents(stack.getComponentsPatch());
        filledWaterskin.remove(DataComponents.DAMAGE);
        filledWaterskin.set(DataComponents.MAX_DAMAGE, Math.max(1, ConfigSettings.WATERSKIN_USES.get()));
        // Set temperature based on temperature of the biome
        filledWaterskin.set(ModItemComponents.WATER_TEMPERATURE,
                            CSMath.clamp((WorldHelper.getTemperatureAt(level, pos)
                                                           - (CSMath.average(ConfigSettings.MAX_TEMP.get(), ConfigSettings.MIN_TEMP.get()))) * 15, -50, 50));
        filledWaterskin.set(DataComponents.MAX_STACK_SIZE, filledWaterskin.getItem().getDefaultMaxStackSize());
        // Set purity of water based on water source, if Thirst Was Taken is loaded
        if (CompatManager.isThirstLoaded())
        {   filledWaterskin = CompatManager.Thirst.setPurityFromBlock(filledWaterskin, pos, level);
        }
        return filledWaterskin;
    }

    public static void handleFillWaterskin(Player player, ItemStack thisStack, InteractionHand usedHand, BlockPos filledAtPos)
    {
        Level level = player.level();
        ItemStack filledWaterskin = getFilledItem(thisStack, level, filledAtPos);

        //Replace 1 of the stack with a FilledWaterskinItem
        if (thisStack.getCount() > 1 || player.getAbilities().instabuild)
        {
            if (!player.addItem(filledWaterskin))
            {
                ItemEntity itementity = player.drop(filledWaterskin, false);
                if (itementity != null)
                {   itementity.setNoPickUpDelay();
                    itementity.setThrower(player);
                }
            }
            thisStack.shrink(1);
        }
        else
        {   player.setItemInHand(usedHand, filledWaterskin);
        }
        player.swing(usedHand);
        player.getCooldowns().addCooldown(filledWaterskin, 10);
        player.awardStat(Stats.ITEM_USED.get(thisStack.getItem()));
        WorldHelper.playEntitySound(ModSounds.WATERSKIN_FILL.value(), player, SoundSource.PLAYERS, 2f, (float) Math.random() / 5 + 0.9f);
    }
}