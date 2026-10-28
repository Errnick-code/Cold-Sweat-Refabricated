package com.momosoftworks.coldsweat.api.temperature.effect.player;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.api.temperature.effect.TempEffectType;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.data.codec.util.IntegerBounds;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import org.joml.Vector4f;

public class HeatVignetteEffect extends AbstractVignetteEffect
{
    public HeatVignetteEffect(TempEffectType<?> type, IntegerBounds bounds)
    {   super(type, bounds);
    }

    private static final Identifier TEXTURE = ColdSweat.createKey("textures/gui/overlay/haze.png");

    @Override
    protected Identifier getTexture()
    {   return TEXTURE;
    }

    @Override
    protected Vector4f getColor(float tickTime)
    {
        float vignetteBrightness = (float) (Math.sin((tickTime + 3) / Math.PI) / 2 + 0.5f);
        return new Vector4f(0.231f, 0f, 0f, vignetteBrightness);
    }

    @Override
    protected void render(float opacity, float tickTime, GuiGraphicsExtractor graphics)
    {
        opacity *= ConfigSettings.HEATSTROKE_BORDER_OPACITY.get();
        super.render(opacity, tickTime, graphics);
    }
}
