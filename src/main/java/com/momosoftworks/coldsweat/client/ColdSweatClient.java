package com.momosoftworks.coldsweat.client;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.client.event.RegisterModels;
import com.momosoftworks.coldsweat.client.gui.BoilerScreen;
import com.momosoftworks.coldsweat.client.gui.HearthScreen;
import com.momosoftworks.coldsweat.client.gui.IceboxScreen;
import com.momosoftworks.coldsweat.client.gui.SewingScreen;
import com.momosoftworks.coldsweat.client.renderer.layer.ChameleonArmorLayer;
import com.momosoftworks.coldsweat.config.ConfigLoadingHandler;
import com.momosoftworks.coldsweat.config.ModUpdater;
import com.momosoftworks.coldsweat.config.core.ConfigManager;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.core.event.client.ClientEventDispatcher;
import com.momosoftworks.coldsweat.core.event.compat.client.EntityRenderersEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.RegisterClientTooltipComponentFactoriesEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.RegisterConditionalItemModelPropertyEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.RegisterFluidModelsEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.RegisterGuiLayersEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.RegisterParticleProvidersEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.RegisterRangeSelectItemModelPropertyEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.RegisterSpecialModelRendererEvent;
import com.momosoftworks.coldsweat.core.init.ModMenus;
import com.momosoftworks.coldsweat.core.network.ClientPacketHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ClientTooltipComponentCallback;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.inventory.MenuType;

import java.lang.reflect.Method;

public class ColdSweatClient implements ClientModInitializer
{
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void onInitializeClient()
    {
        ConfigManager.loadAll();
        ModUpdater.updateConfigs();
        ConfigLoadingHandler.ClientConfigs.loadClientConfigs();

        ClientPacketHandlers.register();
        ModEvents.registerAnnotatedClasses("com.momosoftworks.coldsweat.client", true);
        ClientEventDispatcher.register();

        ModEvents.post(new RegisterGuiLayersEvent());
        ModEvents.post(new RegisterParticleProvidersEvent());
        ModEvents.post(new RegisterClientTooltipComponentFactoriesEvent());
        ModEvents.post(new EntityRenderersEvent.RegisterLayerDefinitions());
        ModEvents.post(new EntityRenderersEvent.RegisterRenderers());
        ModEvents.post(new RegisterRangeSelectItemModelPropertyEvent());
        ModEvents.post(new RegisterConditionalItemModelPropertyEvent());
        ModEvents.post(new RegisterSpecialModelRendererEvent());
        ModEvents.post(new RegisterFluidModelsEvent());

        ClientTooltipComponentCallback.EVENT.register(RegisterClientTooltipComponentFactoriesEvent::create);

        LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType, renderer, helper, context) ->
        {
            if (renderer instanceof LivingEntityRenderer livingRenderer && livingRenderer.getModel() instanceof HumanoidModel)
            {   helper.register(new ChameleonArmorLayer<>(livingRenderer));
            }
        });

        MenuScreens.register(ModMenus.BOILER_CONTAINER_TYPE.value(), BoilerScreen::new);
        MenuScreens.register(ModMenus.ICEBOX_CONTAINER_TYPE.value(), IceboxScreen::new);
        MenuScreens.register(ModMenus.SEWING_CONTAINER_TYPE.value(), SewingScreen::new);
        MenuScreens.register(ModMenus.HEARTH_CONTAINER_TYPE.value(), HearthScreen::new);

        ColdSweat.LOGGER.info("Cold Sweat client initialized");
    }
}