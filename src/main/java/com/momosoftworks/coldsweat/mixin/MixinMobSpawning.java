package com.momosoftworks.coldsweat.mixin;

import com.google.common.collect.Multimap;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.data.codec.configuration.SpawnBiomeData;
import com.momosoftworks.coldsweat.data.codec.util.FunctionalSpawnerData;
import com.momosoftworks.coldsweat.util.serialization.RegistryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(NaturalSpawner.class)
public class MixinMobSpawning
{
    @Inject(method = "mobsAt", at = @At("RETURN"), cancellable = true)
    private static void coldsweat$addConfiguredSpawns(ServerLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator,
                                                      MobCategory category, BlockPos pos, Holder<Biome> biome,
                                                      CallbackInfoReturnable<WeightedList<MobSpawnSettings.SpawnerData>> cir)
    {
        Multimap<Holder<Biome>, SpawnBiomeData> spawnMap = ConfigSettings.ENTITY_SPAWN_BIOMES.get(level.registryAccess());
        if (spawnMap.isEmpty() || (!spawnMap.containsKey(biome) && !spawnMap.containsKey(null))) return;

        List<SpawnBiomeData> configured = new ArrayList<>();
        Set<EntityType<?>> overridden = new HashSet<>();
        for (SpawnBiomeData data : spawnMap.get(biome))
        {
            if (data.category() != category) continue;
            List<EntityType<?>> types = RegistryHelper.mapBuiltinRegistryTagList(BuiltInRegistries.ENTITY_TYPE, data.entities());
            if (types.isEmpty()) continue;
            configured.add(data);
            overridden.addAll(types);
        }
        if (configured.isEmpty()) return;

        List<Weighted<MobSpawnSettings.SpawnerData>> entries = new ArrayList<>(cir.getReturnValue().unwrap());
        entries.removeIf(entry -> overridden.contains(entry.value().type()));

        for (SpawnBiomeData data : configured)
        {
            for (EntityType<?> type : RegistryHelper.mapBuiltinRegistryTagList(BuiltInRegistries.ENTITY_TYPE, data.entities()))
            {
                MobSpawnSettings.SpawnerData spawnerData = FunctionalSpawnerData.create(type, spawnCountMin(data), spawnCountMax(data),
                                                                                       (lvl, structures, generator, cat, spawn, spawnPos) ->
                                                                                       data.location().test(req -> req.test(lvl, spawnPos)));
                entries.add(new Weighted<>(spawnerData, data.weight()));
            }
        }
        cir.setReturnValue(WeightedList.of(entries));
    }

    @Inject(method = "canSpawnMobAt", at = @At("RETURN"), cancellable = true)
    private static void coldsweat$checkSpawnConditions(ServerLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator,
                                                       MobCategory category, MobSpawnSettings.SpawnerData spawnerData, BlockPos pos,
                                                       CallbackInfoReturnable<Boolean> cir)
    {
        if (!cir.getReturnValue()) return;
        if (!FunctionalSpawnerData.canSpawn(level, structureManager, chunkGenerator, category, spawnerData, pos))
        {   cir.setReturnValue(false);
        }
    }

    private static int spawnCountMin(SpawnBiomeData data)
    {   return Math.max(1, data.count().min());
    }

    private static int spawnCountMax(SpawnBiomeData data)
    {
        int min = spawnCountMin(data);
        int max = data.count().max();
        return max == Integer.MAX_VALUE ? min : Math.max(min, max);
    }
}
