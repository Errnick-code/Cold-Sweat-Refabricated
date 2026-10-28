package com.momosoftworks.coldsweat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.compat.EntityAttributeModificationEvent;
import com.momosoftworks.coldsweat.core.init.ModAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(Player.class)
public class MixinPlayerAttributes
{
    @ModifyReturnValue(method = "createAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder coldsweat$addTempAttributes(AttributeSupplier.Builder builder)
    {
        builder.add(ModAttributes.COLD_DAMPENING, Double.NaN);
        builder.add(ModAttributes.HEAT_DAMPENING, Double.NaN);
        builder.add(ModAttributes.COLD_RESISTANCE, Double.NaN);
        builder.add(ModAttributes.HEAT_RESISTANCE, Double.NaN);
        builder.add(ModAttributes.BURNING_POINT, Double.NaN);
        builder.add(ModAttributes.FREEZING_POINT, Double.NaN);
        builder.add(ModAttributes.BASE_BODY_TEMPERATURE, Double.NaN);
        builder.add(ModAttributes.WORLD_TEMPERATURE, Double.NaN);
        builder.add(ModAttributes.TEMP_RATE, Double.NaN);

        EntityAttributeModificationEvent event = new EntityAttributeModificationEvent();
        ModEvents.post(event);
        for (Map.Entry<net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute>, Double> entry
                : event.getAttributes(EntityType.PLAYER).getValues().entrySet())
        {   builder.add(entry.getKey(), entry.getValue());
        }
        return builder;
    }
}
