package com.momosoftworks.coldsweat.util.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class DummyPlayer extends Player
{
    public DummyPlayer(Level level)
    {   super(level, new GameProfile(UUID.randomUUID(), "DummyPlayer"));
    }

    @Override
    public GameType gameMode()
    {   return GameType.SURVIVAL;
    }

    @Override
    public boolean isSpectator()
    {   return false;
    }

    @Override
    public boolean isCreative()
    {   return false;
    }
}