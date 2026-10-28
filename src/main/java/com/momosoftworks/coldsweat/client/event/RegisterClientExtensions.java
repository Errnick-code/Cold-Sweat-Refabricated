package com.momosoftworks.coldsweat.client.event;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.core.event.SubscribeEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.FluidTintSource;
import com.momosoftworks.coldsweat.core.event.compat.client.RegisterFluidModelsEvent;
import com.momosoftworks.coldsweat.core.init.ModFluids;
import com.momosoftworks.coldsweat.util.math.CSMath;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Client-side rendering hooks for Cold Sweat's fluids
 */
public class RegisterClientExtensions
{
    private static final FluidTintSource SLUSH_TINT = new FluidTintSource()
    {
        @Override
        public int color(BlockState state)
        {   return ARGB.color(240, 210, 240, 255);
        }

        @Override
        public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos)
        {
            int color = BiomeColors.getAverageWaterColor(level, pos);
            int alphaColor = ARGB.color(240, ARGB.red(color), ARGB.green(color), ARGB.blue(color));
            int white = ARGB.color(240, 240, 255, 255);
            return CSMath.blendColors(alphaColor, white, 0.5f);
        }
    };

    @SubscribeEvent
    public static void registerFluidModels(RegisterFluidModelsEvent event)
    {
        event.register(new FluidModel.Unbaked(new Material(ColdSweat.createKey("block/slush_still"), true),
                                              new Material(ColdSweat.createKey("block/slush_flow"), true),
                                              null, SLUSH_TINT),
                       ModFluids.SLUSH.value(), ModFluids.FLOWING_SLUSH.value());
    }
}
