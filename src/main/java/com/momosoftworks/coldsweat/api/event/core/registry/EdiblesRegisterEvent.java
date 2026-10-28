package com.momosoftworks.coldsweat.api.event.core.registry;

import com.momosoftworks.coldsweat.common.entity.data.edible.ChameleonEdibles;
import com.momosoftworks.coldsweat.common.entity.data.edible.Edible;
import com.momosoftworks.coldsweat.core.event.Event;

public class EdiblesRegisterEvent extends Event
{
    public final void registerEdible(Edible edible)
    {
        ChameleonEdibles.addEdible(edible);
    }
}
