package com.momosoftworks.coldsweat.data;

import com.momosoftworks.coldsweat.core.init.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class LootTableModifiers
{
    private static final Identifier GOAT_LOOT = Identifier.withDefaultNamespace("entities/goat");
    private static final Identifier HOGLIN_LOOT = Identifier.withDefaultNamespace("entities/hoglin");
    private static final Identifier PIGLIN_BARTER_LOOT = Identifier.withDefaultNamespace("gameplay/piglin_bartering");

    public static void init()
    {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) ->
        {
            if (!source.isBuiltin()) return;

            Identifier id = key.identifier();
            if (id.equals(GOAT_LOOT))
            {
                tableBuilder.withPool(LootPool.lootPool()
                                              .add(LootItem.lootTableItem(ModItems.GOAT_FUR.value())));
            }
            else if (id.equals(HOGLIN_LOOT))
            {
                tableBuilder.withPool(LootPool.lootPool()
                                              .add(LootItem.lootTableItem(ModItems.HOGLIN_HIDE.value())
                                                           .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))));
            }
            else if (id.equals(PIGLIN_BARTER_LOOT))
            {
                tableBuilder.withPool(LootPool.lootPool()
                                              .when(LootItemRandomChanceCondition.randomChance(0.13f))
                                              .add(LootItem.lootTableItem(ModItems.SOUL_SPROUT.value()).setWeight(30)
                                                           .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5))))
                                              .add(LootItem.lootTableItem(ModItems.HOGLIN_HIDE.value()).setWeight(40)
                                                           .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))));
            }
        });

        LootTableEvents.MODIFY_DROPS.register((table, context, drops) ->
        {
            if (context.getOptionalParameter(net.minecraft.world.level.storage.loot.parameters.LootContextParams.THIS_ENTITY) instanceof Hoglin)
            {
                drops.removeIf(stack -> stack.is(Items.LEATHER));
            }
        });
    }
}
