package com.momosoftworks.coldsweat.core.network.message;
import com.momosoftworks.coldsweat.util.ClientOnlyHelper;

import com.momosoftworks.coldsweat.common.data.PersistentData;

import com.momosoftworks.coldsweat.ColdSweat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import com.momosoftworks.coldsweat.core.network.CSNetContext;

public class SyncForgeDataMessage implements CustomPacketPayload
{
    public static final Type<SyncForgeDataMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(ColdSweat.MOD_ID, "sync_forge_data"));
    public static final StreamCodec<FriendlyByteBuf, SyncForgeDataMessage> CODEC = CustomPacketPayload.codec(SyncForgeDataMessage::encode, SyncForgeDataMessage::decode);

    CompoundTag forgeData;
    int entityID;
    String dimension;

    public SyncForgeDataMessage(Entity entity)
    {
        this.forgeData = PersistentData.get(entity);
        this.entityID = entity.getId();
        this.dimension = entity.level().dimension().identifier().toString();
    }

    public SyncForgeDataMessage(CompoundTag forgeData, int entityID, String dimension)
    {
        this.forgeData = forgeData;
        this.entityID = entityID;
        this.dimension = dimension;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeNbt(this.forgeData);
        buffer.writeInt(this.entityID);
        buffer.writeUtf(this.dimension);
    }

    public static SyncForgeDataMessage decode(FriendlyByteBuf buffer)
    {   return new SyncForgeDataMessage(buffer.readNbt(), buffer.readInt(), buffer.readUtf());
    }

    public static void handle(SyncForgeDataMessage message, CSNetContext context)
    {
        context.enqueueWork(() ->
        {
            Level level = ClientOnlyHelper.getClientLevel();
            if (level != null && level.dimension().identifier().toString().equals(message.dimension))
            {
                Entity entity = level.getEntity(message.entityID);
                if (entity != null)
                {   PersistentData.get(entity).merge(message.forgeData);
                }
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {   return TYPE;
    }
}