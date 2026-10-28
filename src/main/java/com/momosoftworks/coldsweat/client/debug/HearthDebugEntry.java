package com.momosoftworks.coldsweat.client.debug;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.client.event.HearthDebugRenderer;
import net.minecraft.client.gui.components.debug.DebugEntryCategory;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.Map;

public class HearthDebugEntry implements DebugScreenEntry
{
    public static final Identifier ID = Identifier.fromNamespaceAndPath(ColdSweat.MOD_ID, "hearth_paths");
    public static final HearthDebugEntry INSTANCE = new HearthDebugEntry();

    @Override
    public void display(DebugScreenDisplayer displayer, Level level, LevelChunk clientChunk, LevelChunk serverChunk)
    {
        int paths = HearthDebugRenderer.HEARTH_LOCATIONS.values().stream().mapToInt(Map::size).sum();
        displayer.addLine("Hearth paths: " + paths);
    }

    @Override
    public DebugEntryCategory category()
    {   return DebugEntryCategory.RENDERER;
    }
}
