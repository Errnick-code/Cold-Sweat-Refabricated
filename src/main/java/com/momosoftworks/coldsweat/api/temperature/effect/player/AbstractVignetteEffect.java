package com.momosoftworks.coldsweat.api.temperature.effect.player;

import net.fabricmc.api.Environment;

import net.fabricmc.api.EnvType;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.ARGB;
import com.momosoftworks.coldsweat.api.temperature.effect.TempEffect;
import com.momosoftworks.coldsweat.core.event.SubscribeEvent;
import com.momosoftworks.coldsweat.core.event.compat.client.RenderGuiLayerEvent;
import com.momosoftworks.coldsweat.api.temperature.effect.TempEffectType;
import com.momosoftworks.coldsweat.data.codec.util.IntegerBounds;
import com.momosoftworks.coldsweat.util.math.CSMath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector4f;

public abstract class AbstractVignetteEffect extends TempEffect
{
    public AbstractVignetteEffect(TempEffectType<?> type, IntegerBounds bounds)
    {   super(type, bounds);
    }

    protected abstract Identifier getTexture();
    protected abstract Vector4f getColor(float tickTime);

    @Environment(EnvType.CLIENT)
    protected void render(float opacity, float tickTime, GuiGraphicsExtractor graphics)
    {
        Vector4f color = this.getColor(tickTime);
        int argb = ARGB.color((int) (opacity * color.w * 255), (int) (color.x * 255), (int) (color.y * 255), (int) (color.z * 255));
        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        graphics.blit(RenderPipelines.GUI_TEXTURED, this.getTexture(), 0, 0, 0f, 0f, width, height, width, height, argb);
    }

    @SubscribeEvent
    public void onRenderGuiLayer(RenderGuiLayerEvent.Pre event)
    {   this.vignette(event.getGuiGraphics());
    }

    @Environment(EnvType.CLIENT)
    public void vignette(GuiGraphicsExtractor graphics)
    {
        LivingEntity entity = Minecraft.getInstance().player;
        if (!this.test(entity)) return;
        float effect = (float) this.getEffectFactor(entity);
        float tickTime = entity.tickCount;

        float opacity = CSMath.blend(0f, 1f, effect, 0, 1);
        if (opacity == 0) return;

        render(opacity, tickTime, graphics);
    }

    @Override
    public Side getSide()
    {   return Side.CLIENT;
    }
}
