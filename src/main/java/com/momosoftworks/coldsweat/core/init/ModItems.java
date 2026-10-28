package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.common.block.BoilerBlock;
import com.momosoftworks.coldsweat.common.block.HearthBottomBlock;
import com.momosoftworks.coldsweat.common.block.IceboxBlock;
import com.momosoftworks.coldsweat.common.block.SewingTableBlock;
import com.momosoftworks.coldsweat.common.block.SmokestackBlock;
import com.momosoftworks.coldsweat.common.block.SoulStalkBlock;
import com.momosoftworks.coldsweat.common.block.ThermolithBlock;
import com.momosoftworks.coldsweat.common.item.ChameleonArmorItem;
import com.momosoftworks.coldsweat.common.item.FilledWaterskinItem;
import com.momosoftworks.coldsweat.common.item.GoatArmorItem;
import com.momosoftworks.coldsweat.common.item.HoglinArmorItem;
import com.momosoftworks.coldsweat.common.item.InsulatedMinecartItem;
import com.momosoftworks.coldsweat.common.item.MinecartInsulationItem;
import com.momosoftworks.coldsweat.common.item.SoulSproutItem;
import com.momosoftworks.coldsweat.common.item.SoulspringLampItem;
import com.momosoftworks.coldsweat.common.item.ThermometerItem;
import com.momosoftworks.coldsweat.common.item.WaterskinItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.UseRemainder;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems
{
    public static final Ref<Item> WATERSKIN = item("waterskin", Item.Properties::new, WaterskinItem::new);

    public static final Ref<Item> FILLED_WATERSKIN = item("filled_waterskin",
            () -> new Item.Properties().stacksTo(1)
                    .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(WATERSKIN.value()))),
            FilledWaterskinItem::new);

    public static final Ref<Item> MINECART_INSULATION = item("minecart_insulation", Item.Properties::new, MinecartInsulationItem::new);

    public static final Ref<Item> THERMOMETER = item("thermometer",
            () -> new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1),
            ThermometerItem::new);

    public static final Ref<Item> SOULSPRING_LAMP = item("soulspring_lamp",
            () -> new Item.Properties().stacksTo(1),
            SoulspringLampItem::new);

    public static final Ref<Item> GOAT_FUR = item("goat_fur", Item.Properties::new, Item::new);
    public static final Ref<Item> HOGLIN_HIDE = item("hoglin_hide", Item.Properties::new, Item::new);

    public static final Ref<Item> INSULATED_MINECART = item("insulated_minecart",
            () -> new Item.Properties().stacksTo(1),
            InsulatedMinecartItem::new);

    public static final Ref<Item> CHAMELEON_MOLT = item("chameleon_molt", Item.Properties::new, Item::new);

    public static final Ref<Item> SLUSH_BUCKET = item("slush_bucket",
            () -> new Item.Properties().stacksTo(1).craftRemainder(net.minecraft.world.item.Items.BUCKET),
            properties -> new BucketItem(ModFluids.SLUSH.value(), properties));

    public static final Ref<Item> HOGLIN_HELMET = item("hoglin_helmet",
            () -> new Item.Properties().stacksTo(1).durability(ArmorType.HELMET.getDurability(14)),
            properties -> new HoglinArmorItem(ModArmorMaterials.HOGLIN.value(), ArmorType.HELMET, properties));

    public static final Ref<Item> HOGLIN_CHESTPLATE = item("hoglin_chestplate",
            () -> new Item.Properties().stacksTo(1).durability(ArmorType.CHESTPLATE.getDurability(14)),
            properties -> new HoglinArmorItem(ModArmorMaterials.HOGLIN.value(), ArmorType.CHESTPLATE, properties));

    public static final Ref<Item> HOGLIN_LEGGINGS = item("hoglin_leggings",
            () -> new Item.Properties().stacksTo(1).durability(ArmorType.LEGGINGS.getDurability(14)),
            properties -> new HoglinArmorItem(ModArmorMaterials.HOGLIN.value(), ArmorType.LEGGINGS, properties));

    public static final Ref<Item> HOGLIN_BOOTS = item("hoglin_boots",
            () -> new Item.Properties().stacksTo(1).durability(ArmorType.BOOTS.getDurability(14)),
            properties -> new HoglinArmorItem(ModArmorMaterials.HOGLIN.value(), ArmorType.BOOTS, properties));

    public static final Ref<Item> GOAT_FUR_HELMET = item("goat_fur_helmet",
            () -> new Item.Properties().stacksTo(1).durability(ArmorType.HELMET.getDurability(10)),
            properties -> new GoatArmorItem(ModArmorMaterials.GOAT_FUR.value(), ArmorType.HELMET, properties));

    public static final Ref<Item> GOAT_FUR_CHESTPLATE = item("goat_fur_chestplate",
            () -> new Item.Properties().stacksTo(1).durability(ArmorType.CHESTPLATE.getDurability(10)),
            properties -> new GoatArmorItem(ModArmorMaterials.GOAT_FUR.value(), ArmorType.CHESTPLATE, properties));

    public static final Ref<Item> GOAT_FUR_LEGGINGS = item("goat_fur_leggings",
            () -> new Item.Properties().stacksTo(1).durability(ArmorType.LEGGINGS.getDurability(10)),
            properties -> new GoatArmorItem(ModArmorMaterials.GOAT_FUR.value(), ArmorType.LEGGINGS, properties));

    public static final Ref<Item> GOAT_FUR_BOOTS = item("goat_fur_boots",
            () -> new Item.Properties().stacksTo(1).durability(ArmorType.BOOTS.getDurability(10)),
            properties -> new GoatArmorItem(ModArmorMaterials.GOAT_FUR.value(), ArmorType.BOOTS, properties));

    public static final Ref<Item> CHAMELEON_HELMET = item("chameleon_helmet",
            () -> new Item.Properties().stacksTo(1).durability(ArmorType.HELMET.getDurability(12)),
            properties -> new ChameleonArmorItem(ModArmorMaterials.CHAMELEON.value(), ArmorType.HELMET, properties));

    public static final Ref<Item> CHAMELEON_CHESTPLATE = item("chameleon_chestplate",
            () -> new Item.Properties().stacksTo(1).durability(ArmorType.CHESTPLATE.getDurability(12)),
            properties -> new ChameleonArmorItem(ModArmorMaterials.CHAMELEON.value(), ArmorType.CHESTPLATE, properties));

    public static final Ref<Item> CHAMELEON_LEGGINGS = item("chameleon_leggings",
            () -> new Item.Properties().stacksTo(1).durability(ArmorType.LEGGINGS.getDurability(12)),
            properties -> new ChameleonArmorItem(ModArmorMaterials.CHAMELEON.value(), ArmorType.LEGGINGS, properties));

    public static final Ref<Item> CHAMELEON_BOOTS = item("chameleon_boots",
            () -> new Item.Properties().stacksTo(1).durability(ArmorType.BOOTS.getDurability(12)),
            properties -> new ChameleonArmorItem(ModArmorMaterials.CHAMELEON.value(), ArmorType.BOOTS, properties));

    public static final Ref<Item> BOILER = item("boiler",
            BoilerBlock::getItemProperties,
            properties -> new BlockItem(ModBlocks.BOILER.value(), properties.useBlockDescriptionPrefix()));

    public static final Ref<Item> ICEBOX = item("icebox",
            IceboxBlock::getItemProperties,
            properties -> new BlockItem(ModBlocks.ICEBOX.value(), properties.useBlockDescriptionPrefix()));

    public static final Ref<Item> SEWING_TABLE = item("sewing_table",
            SewingTableBlock::getItemProperties,
            properties -> new BlockItem(ModBlocks.SEWING_TABLE.value(), properties.useBlockDescriptionPrefix()));

    public static final Ref<Item> HEARTH = item("hearth",
            HearthBottomBlock::getItemProperties,
            properties -> new BlockItem(ModBlocks.HEARTH_BOTTOM.value(), properties.useBlockDescriptionPrefix()));

    public static final Ref<Item> THERMOLITH = item("thermolith",
            ThermolithBlock::getItemProperties,
            properties -> new BlockItem(ModBlocks.THERMOLITH.value(), properties.useBlockDescriptionPrefix()));

    public static final Ref<Item> SOUL_SPROUT = item("soul_sprout",
            () -> SoulStalkBlock.getItemProperties().food(new FoodProperties.Builder()
                                                                  .nutrition(3)
                                                                  .saturationModifier(0.5f)
                                                                  .alwaysEdible()
                                                                  .build()),
            properties -> new SoulSproutItem(ModBlocks.SOUL_STALK.value(), properties));

    public static final Ref<Item> SMOKESTACK = item("smokestack",
            SmokestackBlock::getItemProperties,
            properties -> new BlockItem(ModBlocks.SMOKESTACK.value(), properties.useBlockDescriptionPrefix()));

    public static final Ref<Item> CHAMELEON_SPAWN_EGG = item("chameleon_spawn_egg",
            () -> new Item.Properties().component(DataComponents.ENTITY_DATA,
                    net.minecraft.world.item.component.TypedEntityData.of(ModEntities.CHAMELEON.value(), new net.minecraft.nbt.CompoundTag())),
            SpawnEggItem::new);

    private static <T extends Item> Ref<T> item(String name, Supplier<Item.Properties> properties, Function<Item.Properties, T> factory)
    {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ColdSweat.createKey(name));
        T item = factory.apply(properties.get().setId(key));
        return new Ref<>(Registry.register(BuiltInRegistries.ITEM, key, item));
    }
}
