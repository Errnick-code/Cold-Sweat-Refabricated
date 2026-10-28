package com.momosoftworks.coldsweat.core.network.message;

import com.momosoftworks.coldsweat.util.Side;

import com.momosoftworks.coldsweat.common.data.PersistentData;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.common.entity.data.Preference;
import com.momosoftworks.coldsweat.util.serialization.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import com.momosoftworks.coldsweat.core.network.CSNetContext;

public class SyncPreferencesMessage implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<SyncPreferencesMessage> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ColdSweat.MOD_ID, "sync_preferred_units"));
    public static final StreamCodec<FriendlyByteBuf, SyncPreferencesMessage> CODEC = CustomPacketPayload.codec(SyncPreferencesMessage::encode, SyncPreferencesMessage::decode);

    EnumMap<Preference, Object> preferences = new EnumMap<>(Preference.class);

    public SyncPreferencesMessage()
    {}

    public static SyncPreferencesMessage create()
    {
        SyncPreferencesMessage message = new SyncPreferencesMessage();
        for (Preference preference : Preference.values())
        {   message.preferences.put(preference, preference.getter().get());
        }
        return message;
    }

    public static void encode(SyncPreferencesMessage message, FriendlyByteBuf buffer)
    {
        AtomicReference<Preference> pref = new AtomicReference<>();
        buffer.writeInt(message.preferences.size());
        message.preferences.forEach((key, value) ->
        {   pref.set(key);
            buffer.writeEnum(key);
            pref.get().writer().encode(buffer, value);
        });
    }

    public static SyncPreferencesMessage decode(FriendlyByteBuf buffer)
    {
        SyncPreferencesMessage message = new SyncPreferencesMessage();
        int size = buffer.readInt();
        for (int i = 0; i < size; i++)
        {
            Preference preference = buffer.readEnum(Preference.class);
            Object value = preference.reader().decode(buffer);
            message.preferences.put(preference, value);
        }
        return message;
    }

    public static void handle(SyncPreferencesMessage message, CSNetContext context)
    {
        context.enqueueWork(() ->
        {   // Save the preferences to the player's persistent data
            savePreferences(context.player(), message.preferences);
        });
    }

    private static void savePreferences(Player player, EnumMap<Preference, Object> preferences)
    {
        CompoundTag nbt = PersistentData.get(player);
        CompoundTag preferencesTag = new CompoundTag();
        for (Preference preference : Preference.values())
        {   preferencesTag.put(preference.key(), NBTHelper.serialize(preferences.get(preference)));
        }
        nbt.put("ColdSweatPreferences", preferencesTag);
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {   return TYPE;
    }
}