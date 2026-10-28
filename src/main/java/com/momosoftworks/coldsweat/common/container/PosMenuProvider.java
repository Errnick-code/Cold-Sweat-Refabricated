package com.momosoftworks.coldsweat.common.container;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class PosMenuProvider<T extends AbstractContainerMenu> implements ExtendedMenuProvider<BlockPos>
{
    private final Component title;
    private final BlockPos pos;
    private final PosMenuFactory<T> factory;

    public PosMenuProvider(Component title, BlockPos pos, PosMenuFactory<T> factory)
    {
        this.title = title;
        this.pos = pos;
        this.factory = factory;
    }

    @Override
    public Component getDisplayName()
    {   return title;
    }

    @Override
    public T createMenu(int id, Inventory inventory, Player player)
    {   return factory.create(id, inventory, pos);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayer player)
    {   return pos;
    }

    @FunctionalInterface
    public interface PosMenuFactory<T extends AbstractContainerMenu>
    {   T create(int id, Inventory inventory, BlockPos pos);
    }
}
