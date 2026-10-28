package com.momosoftworks.coldsweat.core.event;

import com.momosoftworks.coldsweat.api.event.core.init.EnableTemperatureEvent;
import com.momosoftworks.coldsweat.api.event.vanilla.ServerConfigsLoadedEvent;
import com.momosoftworks.coldsweat.common.capability.handler.EntityTempManager;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.core.init.ModAttributes;
import com.momosoftworks.coldsweat.core.init.ModEntities;
import com.momosoftworks.coldsweat.core.event.compat.EntityAttributeModificationEvent;
import com.momosoftworks.coldsweat.util.reflection.ReflectionHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;

import java.util.HashMap;
import java.util.Map;

import static com.momosoftworks.coldsweat.common.capability.handler.EntityTempManager.TEMPERATURE_ENABLED_ENTITIES;

public class AddTempAttributes
{
    public static class ForPlayer
    {
        @SubscribeEvent
        public static void addPlayerAttributes(EntityAttributeModificationEvent event)
        {
            event.add(EntityType.PLAYER, ModAttributes.COLD_DAMPENING, Double.NaN);
            event.add(EntityType.PLAYER, ModAttributes.HEAT_DAMPENING, Double.NaN);
            event.add(EntityType.PLAYER, ModAttributes.COLD_RESISTANCE, Double.NaN);
            event.add(EntityType.PLAYER, ModAttributes.HEAT_RESISTANCE, Double.NaN);
            event.add(EntityType.PLAYER, ModAttributes.BURNING_POINT, Double.NaN);
            event.add(EntityType.PLAYER, ModAttributes.FREEZING_POINT, Double.NaN);
            event.add(EntityType.PLAYER, ModAttributes.BASE_BODY_TEMPERATURE, Double.NaN);
            event.add(EntityType.PLAYER, ModAttributes.WORLD_TEMPERATURE, Double.NaN);
            event.add(EntityType.PLAYER, ModAttributes.TEMP_RATE, Double.NaN);
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<EntityType<? extends LivingEntity>, AttributeSupplier> getSuppliers()
    {
        try
        {   return (Map<EntityType<? extends LivingEntity>, AttributeSupplier>)
                    ReflectionHelper.findField(DefaultAttributes.class, "SUPPLIERS").get(null);
        }
        catch (IllegalAccessException e)
        {   throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<Holder<Attribute>, AttributeInstance> getInstances(AttributeSupplier supplier)
    {
        try
        {   return (Map<Holder<Attribute>, AttributeInstance>)
                    ReflectionHelper.findField(AttributeSupplier.class, "instances").get(supplier);
        }
        catch (IllegalAccessException e)
        {   throw new RuntimeException(e);
        }
    }

    public static AttributeSupplier getAttributes(EntityType<? extends LivingEntity> type)
    {
        AttributeSupplier supplier = DefaultAttributes.getSupplier(type);
        return supplier;
    }

    @SubscribeEvent
    public static void onEntitiesCreated(ServerConfigsLoadedEvent event)
    {
        Map<EntityType<? extends LivingEntity>, AttributeSupplier> suppliers = getSuppliers();

        for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE)
        {
            EntityType<? extends LivingEntity> type;
            try
            {   type = (EntityType<? extends LivingEntity>) entityType;
            }
            catch (ClassCastException e)
            {   continue;
            }

            if (type == EntityType.PLAYER) continue;

            EnableTemperatureEvent enableEvent = new EnableTemperatureEvent(type);
            ModEvents.post(enableEvent);
            if (!enableEvent.isEnabled() || enableEvent.isCanceled()) continue;

            TEMPERATURE_ENABLED_ENTITIES.add(type);

            AttributeSupplier attributes = suppliers.get(type);
            if (attributes == null) continue;

            AttributeSupplier.Builder builder = AttributeSupplier.builder();
            for (Holder<Attribute> holder : getInstances(attributes).keySet())
            {   builder.add(holder, attributes.getBaseValue(holder));
            }

            builder.add(ModAttributes.COLD_DAMPENING, Double.NaN);
            builder.add(ModAttributes.HEAT_DAMPENING, Double.NaN);
            builder.add(ModAttributes.COLD_RESISTANCE, Double.NaN);
            builder.add(ModAttributes.HEAT_RESISTANCE, Double.NaN);
            builder.add(ModAttributes.BURNING_POINT, Double.NaN);
            builder.add(ModAttributes.FREEZING_POINT, Double.NaN);
            builder.add(ModAttributes.BASE_BODY_TEMPERATURE, Double.NaN);
            builder.add(ModAttributes.WORLD_TEMPERATURE, Double.NaN);
            builder.add(ModAttributes.TEMP_RATE, Double.NaN);

            try
            {   ReflectionHelper.findField(AttributeSupplier.class, "instances").set(attributes, getInstances(builder.build()));
            }
            catch (Exception e)
            {   continue;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEnableTemperatureEvent(EnableTemperatureEvent event)
    {
        if (event.getEntityType() == ModEntities.CHAMELEON
        .value() || (ConfigSettings.ENABLE_ENTITY_CLIMATES.get() && EntityTempManager.hasClimateData(event.getEntityType())))
        {   event.setEnabled(true);
        }
    }
}