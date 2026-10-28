package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.api.event.core.registry.EdiblesRegisterEvent;
import com.momosoftworks.coldsweat.common.entity.data.edible.*;
import com.momosoftworks.coldsweat.core.event.SubscribeEvent;
import com.momosoftworks.coldsweat.core.event.compat.ServerStartingEvent;
public class EdibleInit
{
    @SubscribeEvent
    public static void onEdiblesRegister(EdiblesRegisterEvent event)
    {
        event.registerEdible(new HotBiomeEdible());
        event.registerEdible(new ColdBiomeEdible());
        event.registerEdible(new HumidBiomeEdible());
        event.registerEdible(new TamingEdible());
    }

    @SubscribeEvent
    public static void onServerStart(ServerStartingEvent event)
    {
        EdiblesRegisterEvent edibleEvent = new EdiblesRegisterEvent();
        ModEvents.post(edibleEvent);
    }
}