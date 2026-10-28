package com.momosoftworks.coldsweat.core.network.message;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.Level;
import net.minecraft.util.ProblemReporter;
import com.momosoftworks.coldsweat.util.ClientOnlyHelper;
import com.momosoftworks.coldsweat.core.network.CSNetContext;

import com.momosoftworks.coldsweat.ColdSweat;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BlockDataUpdateMessage implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<BlockDataUpdateMessage> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ColdSweat.MOD_ID, "block_data_update"));
    public static final StreamCodec<FriendlyByteBuf, BlockDataUpdateMessage> CODEC = CustomPacketPayload.codec(BlockDataUpdateMessage::encode, BlockDataUpdateMessage::decode);

    BlockPos blockPos;
    CompoundTag tag;

    public BlockDataUpdateMessage(BlockEntity blockEntity)
    {
        this.blockPos = blockEntity.getBlockPos();
        this.tag = blockEntity.getUpdateTag(blockEntity.getLevel().registryAccess());
    }

    public BlockDataUpdateMessage(BlockPos blockPos, CompoundTag tag)
    {
        this.blockPos = blockPos;
        this.tag = tag;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(this.blockPos);
        buffer.writeNbt(this.tag);
    }

    public static BlockDataUpdateMessage decode(FriendlyByteBuf buffer)
    {
        return new BlockDataUpdateMessage(buffer.readBlockPos(), buffer.readNbt());
    }

    public static void handle(BlockDataUpdateMessage message, CSNetContext context)
    {
        context.enqueueWork(() ->
        {
            Level level = ClientOnlyHelper.getClientLevel();
            if (level != null)
            {
                BlockEntity be = level.getBlockEntity(message.blockPos);
                if (be != null)
                {   be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, context.player().registryAccess(), message.tag));
                }
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
