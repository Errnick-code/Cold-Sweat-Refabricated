package com.momosoftworks.coldsweat.api.temperature.effect.player;

import com.momosoftworks.coldsweat.api.temperature.effect.TempEffectType;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.data.codec.util.IntegerBounds;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import org.joml.Vector4f;

public class FreezeVignetteEffect extends AbstractVignetteEffect
{
    public FreezeVignetteEffect(TempEffectType<?> type, IntegerBounds bounds)
    {   super(type, bounds);
    }

    static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/misc/powder_snow_outline.png");

    @Override
    protected Identifier getTexture()
    {   return TEXTURE;
    }

    @Override
    protected Vector4f getColor(float tickTime)
    {   return new Vector4f(1f, 1f, 1f, 1f);
    }

    @Override
    public void vignette(GuiGraphicsExtractor graphics)
    {   super.vignette(graphics);
    }

    @Override
    protected void render(float opacity, float tickTime, GuiGraphicsExtractor graphics)
    {
        opacity *= ConfigSettings.FREEZING_OVERLAY_OPACITY.get();
        super.render(opacity, tickTime, graphics);
    }
}
