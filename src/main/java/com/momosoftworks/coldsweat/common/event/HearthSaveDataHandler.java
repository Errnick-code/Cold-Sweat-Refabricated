package com.momosoftworks.coldsweat.common.event;

import com.momosoftworks.coldsweat.common.data.PersistentData;

import com.mojang.datafixers.util.Pair;
import com.momosoftworks.coldsweat.core.network.message.DisableHearthParticlesMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import com.momosoftworks.coldsweat.core.event.SubscribeEvent;
import com.momosoftworks.coldsweat.core.event.compat.EntityJoinLevelEvent;
import com.momosoftworks.coldsweat.core.event.compat.PlayerEvent;

import java.util.HashSet;
import java.util.Set;
import com.momosoftworks.coldsweat.core.network.CSNet;
public class HearthSaveDataHandler
{
    public static final Set<Pair<BlockPos, Identifier>> HEARTH_POSITIONS = new HashSet<>();
    public static final Set<Pair<BlockPos, Identifier>> DISABLED_HEARTHS = new HashSet<>();

    public static CompoundTag serializeDisabledHearths()
    {
        CompoundTag tag = new CompoundTag();
        ListTag disabledHearths = new ListTag();

        for (Pair<BlockPos, Identifier> pair : DISABLED_HEARTHS)
        {
            CompoundTag hearthData = new CompoundTag();
            hearthData.putLong("Pos", pair.getFirst().asLong());
            hearthData.putString("Level", pair.getSecond().toString());
            disabledHearths.add(hearthData);
        }
        tag.put("DisabledHearths", disabledHearths);
        return tag;
    }

    public static void deserializeDisabledHearths(CompoundTag disabledHearths)
    {
        DISABLED_HEARTHS.clear();
        for (Tag tag : disabledHearths.getListOrEmpty("DisabledHearths"))
        {
            CompoundTag hearthData = (CompoundTag) tag;
            DISABLED_HEARTHS.add(Pair.of(BlockPos.of(hearthData.getLongOr("Pos", 0L)), Identifier.parse(hearthData.getStringOr("Level", ""))));
        }
    }

    /**
     * Load the player's disabled Hearths on login
     */
    @SubscribeEvent
    public static void loadDisabledHearths(EntityJoinLevelEvent event)
    {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player)
        {
            CompoundTag disabledHearths = new CompoundTag();
            disabledHearths.put("DisabledHearths", PersistentData.get(player).getListOrEmpty("DisabledHearths"));
            CSNet.sendToPlayer(player, new DisableHearthParticlesMessage(disabledHearths));
        }
    }

    @SubscribeEvent
    public static void transferDisabledHearths(PlayerEvent.Clone event)
    {
        if (!event.getEntity().level().isClientSide())
        {
            ListTag disabledHearths = PersistentData.get(event.getOriginal()).getListOrEmpty("DisabledHearths");
            PersistentData.get(event.getEntity()).put("DisabledHearths", disabledHearths);
        }
    }
}