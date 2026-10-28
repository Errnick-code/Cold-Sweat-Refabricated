package com.momosoftworks.coldsweat.compat.serene_seasons;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.init.ModConfig;

public final class SereneSeasonsCompat
{
    private SereneSeasonsCompat() {}

    public static boolean isColdEnoughToSnow(Level level, BlockPos pos)
    {
        if (!ModConfig.seasons.generateSnowAndIce) return false;
        if (!ModConfig.seasons.isDimensionWhitelisted(level.dimension())) return false;

        ISeasonState season = SeasonHelper.getSeasonState(level);
        return season.getSeason() == Season.WINTER;
    }
}
