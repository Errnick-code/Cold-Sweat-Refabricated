package com.momosoftworks.coldsweat.compat.create;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.api.util.Temperature;
import com.momosoftworks.coldsweat.common.blockentity.ThermolithBlockEntity;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.core.init.ModBlocks;
import com.momosoftworks.coldsweat.util.world.WorldHelper;
import com.zurrtum.create.api.behaviour.display.DisplaySource;
import com.zurrtum.create.api.registry.CreateRegistries;
import com.zurrtum.create.content.redstone.displayLink.DisplayLinkContext;
import com.zurrtum.create.content.redstone.displayLink.source.SingleLineDisplaySource;
import com.zurrtum.create.content.redstone.displayLink.target.DisplayTargetStats;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ColdSweatDisplaySources
{
    public static final DisplaySource THERMOLITH = new Thermolith();

    public static void register()
    {
        Registry.register(CreateRegistries.DISPLAY_SOURCE, ColdSweat.createKey("cold_sweat_thermolith"), THERMOLITH);
        DisplaySource.BY_BLOCK.add(ModBlocks.THERMOLITH.value(), THERMOLITH);
    }

    public static class Thermolith extends SingleLineDisplaySource
    {
        @Override
        protected MutableComponent provideLine(DisplayLinkContext displayLinkContext, DisplayTargetStats displayTargetStats)
        {
            if (displayLinkContext.getSourceBlockEntity() instanceof ThermolithBlockEntity thermolith)
            {
                double temperature = WorldHelper.getTemperatureAt(thermolith.getLevel(), thermolith.getBlockPos());
                Temperature.Units units = ConfigSettings.UNITS.get();
                double convertedTemp = Temperature.convert(temperature, Temperature.Units.MC, units, true);
                String places = units == Temperature.Units.MC ? "%.2f" : "%.0f";
                String text = String.format(places + "%s", convertedTemp, units.getFormattedName().getString());
                return Component.literal(text);
            }
            return Component.empty();
        }

        @Override
        public boolean allowsLabeling(DisplayLinkContext displayLinkContext)
        {   return true;
        }

        @Override
        public Component getName()
        {   return Component.translatable("block.cold_sweat.thermolith");
        }
    }
}
