package com.momosoftworks.coldsweat.util;

import com.momosoftworks.coldsweat.client.event.HearthDebugRenderer;
import com.momosoftworks.coldsweat.client.gui.config.pages.ConfigPageOne;
import com.momosoftworks.coldsweat.common.blockentity.HearthBlockEntity;
import com.momosoftworks.coldsweat.util.reflection.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * This class is an abstraction layer for some methods in client-oriented classes
 * so the server doesn't throw a fit when it tries to load the class on the wrong side.
 */
public class ClientOnlyHelper
{
    public static void playEntitySound(SoundEvent sound, SoundSource source, float volume, float pitch, Entity entity)
    {   Minecraft.getInstance().getSoundManager().play(new EntityBoundSoundInstance(sound, source, volume, pitch, entity, entity.level().getRandom().nextLong()));
    }

    public static Level getClientLevel()
    {   return Minecraft.getInstance().level;
    }

    public static void addHearthPosition(BlockPos pos)
    {   HearthDebugRenderer.HEARTH_LOCATIONS.put(pos, new HashMap<>());
    }

    public static void removeHearthPosition(BlockPos pos)
    {   HearthDebugRenderer.HEARTH_LOCATIONS.remove(pos);
    }

    public static void updateHearthDebug(HearthBlockEntity hearth)
    {   HearthDebugRenderer.updatePaths(hearth);
    }

    public static void openConfigScreen()
    {   Minecraft.getInstance().setScreen(new ConfigPageOne(Minecraft.getInstance().screen));
    }

    public static Player getClientPlayer()
    {   return Minecraft.getInstance().player;
    }

    public static void sendPacketToServer(ServerboundSetCreativeModeSlotPacket packet)
    {   Minecraft.getInstance().getConnection().send(packet);
    }

    public static GameType getGameMode()
    {   return Minecraft.getInstance().gameMode.getPlayerMode();
    }

    private static final Field SLIM = ReflectionHelper.findField(PlayerModel.class, "slim");

    public static boolean isPlayerModelSlim(RenderLayer<?, ?> layer)
    {
        if (layer.getParentModel() instanceof PlayerModel playerModel)
        {
            try
            {   return (boolean) SLIM.get(playerModel);
            }
            catch (IllegalAccessException e)
            {   e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isPlayerModelSlim(HumanoidModel<?> model)
    {
        if (model instanceof PlayerModel playerModel)
        {
            try
            {   return (boolean) SLIM.get(playerModel);
            }
            catch (IllegalAccessException e)
            {   e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Before 1.21.6, text colors with (near) zero alpha were drawn as opaque. They are now skipped entirely,
     * so RGB-only colors must be promoted to ARGB.
     */
    public static int legacyTextColor(int color)
    {   return (color & 0xFC000000) == 0 ? color | 0xFF000000 : color;
    }

    public static void renderVerticalCropText(String text, int x, int y, int height, int color, GuiGraphicsExtractor graphics)
    {
        Font font = Minecraft.getInstance().font;

        if (height > 0)
        {
            // Only render the bottom portion of the text
            graphics.enableScissor(x, y + font.lineHeight - height, x + font.width(text), y + font.lineHeight);
            graphics.text(font, text, x, y, legacyTextColor(color), false);
            graphics.disableScissor();
        }
    }
}
