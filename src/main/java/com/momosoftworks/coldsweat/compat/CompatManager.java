package com.momosoftworks.coldsweat.compat;

import com.momosoftworks.coldsweat.api.event.core.init.FetchSeasonsModsEvent;
import com.momosoftworks.coldsweat.compat.trinkets.TrinketsCompat;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CompatManager
{
    private static final List<String> SEASONS_MODS = new ArrayList<>();

    public static boolean modLoaded(String modID)
    {   return FabricLoader.getInstance().isModLoaded(modID);
    }

    private static List<String> fetchSeasonsMods()
    {
        if (SEASONS_MODS.isEmpty())
        {
            FetchSeasonsModsEvent event = new FetchSeasonsModsEvent();
            if (isSereneSeasonsLoaded())
            {   event.addSeasonsMod("sereneseasons");
            }
            ModEvents.post(event);
            SEASONS_MODS.addAll(event.getSeasonsMods());
        }
        return SEASONS_MODS;
    }

    public static List<String> getSeasonsMods()
    {   return fetchSeasonsMods();
    }

    public static boolean isBiomesOPlentyLoaded()
    {   return modLoaded("biomesoplenty");
    }
    public static boolean isSereneSeasonsLoaded()
    {   return modLoaded("sereneseasons");
    }
    public static boolean isCuriosLoaded()
    {   return modLoaded("curios") || isTrinketsLoaded();
    }
    public static boolean isTrinketsLoaded()
    {   return modLoaded("trinkets_updated");
    }
    public static boolean isSpiritLoaded()
    {   return modLoaded("spirit");
    }
    public static boolean isBiomesYoullGoLoaded()
    {   return modLoaded("byg");
    }
    public static boolean isBiomesWeveGoneLoaded()
    {   return modLoaded("biomeswevegone");
    }
    public static boolean isCreateLoaded()
    {   return modLoaded("create");
    }
    public static boolean isAtmosphericLoaded()
    {   return modLoaded("atmospheric");
    }
    public static boolean isEnvironmentalLoaded()
    {   return modLoaded("environmental");
    }
    public static boolean isTerralithLoaded()
    {   return modLoaded("terralith");
    }
    public static boolean isWeather2Loaded()
    {   return modLoaded("weather2");
    }
    public static boolean isWythersLoaded()
    {   return modLoaded("wwoo");
    }
    public static boolean isPrimalWinterLoaded()
    {   return modLoaded("primalwinter");
    }
    public static boolean isThirstLoaded()
    {   return modLoaded("thirstwastaken2");
    }
    public static boolean isIcebergLoaded()
    {   return modLoaded("iceberg");
    }
    public static boolean isSupplementariesLoaded()
    {   return modLoaded("supplementaries");
    }
    public static boolean isValkyrienSkiesLoaded()
    {   return modLoaded("valkyrienskies");
    }
    public static boolean isSableLoaded()
    {   return modLoaded("sable");
    }
    public static boolean isSublevelCompatLoaded()
    {   return isValkyrienSkiesLoaded() || isSableLoaded();
    }
    public static boolean isToughAsNailsLoaded()
    {   return modLoaded("toughasnails");
    }
    public static boolean isTwilightForestLoaded()
    {   return modLoaded("twilightforest");
    }
    public static boolean isAetherLoaded()
    {   return modLoaded("aether");
    }
    public static boolean isRegionsUnexploredLoaded()
    {   return modLoaded("regions_unexplored");
    }

    public static abstract class Curios
    {
        public static boolean hasCurio(LivingEntity entity, ItemStack curio)
        {
            if (isTrinketsLoaded())
            {   return TrinketsCompat.hasEquipped(entity, stack -> stack.is(curio.getItem()));
            }
            return false;
        }

        public static List<ItemStack> getCurios(LivingEntity entity)
        {
            if (isTrinketsLoaded())
            {   return TrinketsCompat.getAllEquipped(entity);
            }
            return new ArrayList<>();
        }
    }

    public static abstract class Create
    {
        public static boolean isFluidPipe(BlockState state)
        {   return isCreateLoaded() && com.momosoftworks.coldsweat.compat.create.CreateCompat.isFluidPipe(state);
        }
    }

    public static abstract class Weather2
    {
        public static boolean isRainstormAt(Level level, BlockPos pos)
        {   return false;
        }

        public static Object getClosestStorm(Level level, BlockPos pos)
        {   return null;
        }
    }

    public static abstract class SereneSeasons
    {
        public static boolean isColdEnoughToSnow(Level level, BlockPos pos)
        {
            return isSereneSeasonsLoaded()
                && com.momosoftworks.coldsweat.compat.serene_seasons.SereneSeasonsCompat.isColdEnoughToSnow(level, pos);
        }
    }

    public static abstract class Thirst
    {
        public static boolean hasPurity(ItemStack stack)
        {
            return isThirstLoaded() && com.momosoftworks.coldsweat.compat.thirst.ThirstCompat.hasPurity(stack);
        }

        public static int getPurity(ItemStack stack)
        {
            return isThirstLoaded() ? com.momosoftworks.coldsweat.compat.thirst.ThirstCompat.getPurity(stack) : 0;
        }

        public static ItemStack setPurity(ItemStack stack, int purity)
        {
            return isThirstLoaded()
                   ? com.momosoftworks.coldsweat.compat.thirst.ThirstCompat.setPurity(stack, purity)
                   : stack;
        }

        public static ItemStack setPurityFromBlock(ItemStack item, BlockPos pos, Level level)
        {
            return isThirstLoaded()
                   ? com.momosoftworks.coldsweat.compat.thirst.ThirstCompat.setPurityFromBlock(item, pos, level)
                   : item;
        }

        public static boolean canBoil(ItemStack stack)
        {
            return isThirstLoaded() && com.momosoftworks.coldsweat.compat.thirst.ThirstCompat.canBoil(stack);
        }

        public static ItemStack boilStep(ItemStack stack)
        {
            return isThirstLoaded()
                   ? com.momosoftworks.coldsweat.compat.thirst.ThirstCompat.boilStep(stack)
                   : stack;
        }
    }

    public static abstract class LegendaryTooltips
    {
        public static int getTooltipStartIndex(List<?> tooltip)
        {   return 0;
        }
    }

    public static abstract class Valkyrien
    {
        public static boolean isInShipyard(Level level, BlockPos pos)
        {   return false;
        }

        public static AABB transformShipToWorld(Level level, AABB aabb)
        {   return aabb;
        }

        public static BlockPos transformShipToWorld(Level level, BlockPos pos)
        {   return pos;
        }

        public static Collection<AABB> transformWorldToShip(Level level, AABB aabb)
        {   return Set.of();
        }

        public static BlockPos transformWorldToShip(Level level, BlockPos pos)
        {   return pos;
        }
    }

    public static abstract class Sable
    {
        public static boolean isInPlotGrid(Level level, BlockPos pos)
        {   return false;
        }

        public static AABB transformSublToWorld(Level level, AABB aabb)
        {   return aabb;
        }

        public static BlockPos transformSublToWorld(Level level, BlockPos pos)
        {   return pos;
        }

        public static Collection<AABB> transformWorldToSubl(Level level, AABB aabb)
        {   return Set.of();
        }

        public static BlockPos transformWorldToSubl(Level level, BlockPos pos)
        {   return pos;
        }
    }
}