package com.momosoftworks.coldsweat;

import com.momosoftworks.coldsweat.api.registry.SpreadRuleRegistry;
import com.momosoftworks.coldsweat.config.ConfigLoadingHandler;
import com.momosoftworks.coldsweat.data.LootTableModifiers;
import com.momosoftworks.coldsweat.config.ModUpdater;
import com.momosoftworks.coldsweat.config.spec.ClientSettingsConfig;
import com.momosoftworks.coldsweat.config.spec.EntitySettingsConfig;
import com.momosoftworks.coldsweat.config.spec.ItemSettingsConfig;
import com.momosoftworks.coldsweat.config.spec.MainSettingsConfig;
import com.momosoftworks.coldsweat.config.spec.WorldSettingsConfig;
import com.momosoftworks.coldsweat.core.event.EventDispatcher;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.PotionRecipes;
import com.momosoftworks.coldsweat.core.init.*;
import com.momosoftworks.coldsweat.core.network.ModPacketHandlers;
import com.momosoftworks.coldsweat.util.registries.ModGameRules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ColdSweat implements ModInitializer
{
    public static final Logger LOGGER = LogManager.getLogger("Cold Sweat");

    public static final String MOD_ID = "cold_sweat";

    @Override
    public void onInitialize()
    {
        ModEvents.registerAnnotatedClasses("com.momosoftworks.coldsweat");
        EventDispatcher.registerCallbacks();

        ModPacketHandlers.registerPayloads();
        ModPacketHandlers.registerServerReceivers();

        registerConfigs();

        registerRegistries();

        com.momosoftworks.coldsweat.core.event.RegisterAttributes.registerAttributes();

        ModGameRules.registerGameRules();
        ModBiomeModifications.register();
        PotionRecipes.init();
        LootTableModifiers.init();
        ModCreativeTabs.register();
        ModCommands.register();
        ModCommands.registerArgumentTypes();

        ModUpdater.updateFileNames();

        ConfigLoadingHandler.initRegistries();

        SpreadRuleRegistry.registerDefaults();

        if (com.momosoftworks.coldsweat.compat.CompatManager.isCreateLoaded())
        {   com.momosoftworks.coldsweat.compat.create.ColdSweatDisplaySources.register();
        }
    }

    private static void registerConfigs()
    {
        MainSettingsConfig.setup();
        WorldSettingsConfig.setup();
        ItemSettingsConfig.setup();
        EntitySettingsConfig.setup();
        if (FabricLoader.getInstance().getEnvironmentType() == net.fabricmc.api.EnvType.CLIENT)
        {
            ClientSettingsConfig.setup();
        }
    }

    private static void registerRegistries()
    {
        ModBlocks.BOILER.value();
        ModFluids.SLUSH.value();
        ModItems.WATERSKIN.value();
        ModEntities.CHAMELEON.value();
        ModBlockEntities.HEARTH.value();
        ModMenus.BOILER_CONTAINER_TYPE.value();
        ModEffects.GRACE.value();
        ModPotions.ICE_RESISTANCE.value();
        ModParticleTypes.MOB_COLD.value();
        ModSounds.BUCKET_FILL_SLUSH.value();
        ModFeatures.SOUL_STALK_FEATURE.value();
        ModAttributes.WORLD_TEMPERATURE.value();
        ModArmorMaterials.HOGLIN.value();
        ModTempEffects.FREEZE_HEALING.value();
        ModAdvancementTriggers.TEMPERATURE_CHANGED.value();
        ModDataAttachments.temperatureAttachment();
        ModDataAttachments.shearableAttachment();
    }

    public static Identifier createKey(String path)
    {   return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static String getVersion()
    {   return FabricLoader.getInstance().getModContainer(MOD_ID)
                              .map(container -> container.getMetadata().getVersion().getFriendlyString())
                              .orElse("unknown");
    }
}