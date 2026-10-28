package com.momosoftworks.coldsweat.core.event.compat.client;

import com.momosoftworks.coldsweat.core.event.Event;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.world.level.material.Fluid;

public class RegisterFluidModelsEvent extends Event
{
    public void register(FluidModel.Unbaked model, Fluid... fluids)
    {
        if (fluids.length == 0) return;
        if (fluids.length == 1)
        {   FluidRenderingRegistry.register(fluids[0], model);
            return;
        }
        for (int i = 0; i < fluids.length - 1; i++)
        {   FluidRenderingRegistry.register(fluids[i], fluids[i + 1], model);
        }
    }
}
