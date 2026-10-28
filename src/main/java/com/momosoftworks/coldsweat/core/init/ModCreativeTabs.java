package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.api.event.client.InsulatorTabBuildEvent;
import com.momosoftworks.coldsweat.api.insulation.Insulation;
import com.momosoftworks.coldsweat.common.item.FilledWaterskinItem;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.core.event.ModEvents;
import com.momosoftworks.coldsweat.data.codec.configuration.InsulatorData;
import com.momosoftworks.coldsweat.util.item.ItemStackHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import com.momosoftworks.coldsweat.common.item.ArmorItemBase;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ModCreativeTabs
{
    public static final CreativeModeTab COLD_SWEAT_TAB = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> FilledWaterskinItem.getDisplayStack())
            .displayItems((params, list) ->
            {
                list.acceptAll(List.of(
                        ModItems.WATERSKIN.value().getDefaultInstance(),
                        ModItems.FILLED_WATERSKIN.value().getDefaultInstance(),
                        ModItems.GOAT_FUR.value().getDefaultInstance(),
                        ModItems.HOGLIN_HIDE.value().getDefaultInstance(),
                        ModItems.CHAMELEON_MOLT.value().getDefaultInstance(),
                        ModItems.MINECART_INSULATION.value().getDefaultInstance(),
                        ModItems.INSULATED_MINECART.value().getDefaultInstance(),
                        ModItems.SOULSPRING_LAMP.value().getDefaultInstance(),
                        ModItems.SOUL_SPROUT.value().getDefaultInstance(),
                        ModItems.THERMOMETER.value().getDefaultInstance(),
                        ModItems.THERMOLITH.value().getDefaultInstance(),
                        ModItems.HEARTH.value().getDefaultInstance(),
                        ModItems.BOILER.value().getDefaultInstance(),
                        ModItems.ICEBOX.value().getDefaultInstance(),
                        ModItems.SMOKESTACK.value().getDefaultInstance(),
                        ModItems.SEWING_TABLE.value().getDefaultInstance(),
                        ModItems.HOGLIN_HELMET.value().getDefaultInstance(),
                        ModItems.HOGLIN_CHESTPLATE.value().getDefaultInstance(),
                        ModItems.HOGLIN_LEGGINGS.value().getDefaultInstance(),
                        ModItems.HOGLIN_BOOTS.value().getDefaultInstance(),
                        ModItems.GOAT_FUR_HELMET.value().getDefaultInstance(),
                        ModItems.GOAT_FUR_CHESTPLATE.value().getDefaultInstance(),
                        ModItems.GOAT_FUR_LEGGINGS.value().getDefaultInstance(),
                        ModItems.GOAT_FUR_BOOTS.value().getDefaultInstance(),
                        ModItems.CHAMELEON_HELMET.value().getDefaultInstance(),
                        ModItems.CHAMELEON_CHESTPLATE.value().getDefaultInstance(),
                        ModItems.CHAMELEON_LEGGINGS.value().getDefaultInstance(),
                        ModItems.CHAMELEON_BOOTS.value().getDefaultInstance(),
                        ModItems.CHAMELEON_SPAWN_EGG.value().getDefaultInstance()
                ));
            })
            .title(Component.translatable("itemGroup.cold_sweat"))
            .build();

    public static final CreativeModeTab INSULATION_ITEMS_TAB = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> ModItems.CHAMELEON_MOLT.value().getDefaultInstance())
            .displayItems((params, list) ->
            {
                List<ItemStack> allInsulators = new ArrayList<>();
                List.of(sort(ConfigSettings.INSULATION_ITEMS.get().entries()),
                        sort(ConfigSettings.INSULATING_ARMORS.get().entries())).stream().flatMap(Collection::stream).forEach(stack ->
                {
                    if (allInsulators.stream().noneMatch(s -> s.getItem() == stack.getItem()))
                    {   allInsulators.add(stack);
                    }
                });

                list.acceptAll(allInsulators);
            })
            .title(Component.translatable("itemGroup.cs_insulation_items"))
            .build();

    public static void register()
    {   Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ColdSweat.createKey("cold_sweat"), COLD_SWEAT_TAB);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ColdSweat.createKey("cs_insulation_items"), INSULATION_ITEMS_TAB);
    }

    private static List<ItemStack> sort(Collection<Map.Entry<Item, InsulatorData>> items)
    {
        List<Map.Entry<Item, InsulatorData>> list = new ArrayList<>(items);
        list.removeIf(entry -> entry.getKey() == null || entry.getKey() == Items.AIR);

        list.sort(Comparator.comparing(entry -> entry.getKey().builtInRegistryHolder().tags().sequential().map(tag -> tag.location().toString()).reduce("", String::concat)));
        list.sort(Comparator.comparingInt(entry -> entry.getValue().insulation().stream().mapToInt(Insulation::getCompareValue).min().orElse(0)));
        list.sort(Comparator.comparing(entry -> {
            if (!(entry.getKey() instanceof ArmorItemBase armor)) return "";
            String materialName = armor.getArmorMaterial().assetId().identifier().toString();
            return materialName + (3 - ItemStackHelper.getEquipmentSlot(entry.getKey().getDefaultInstance()).getIndex());
        }));

        InsulatorTabBuildEvent event = new InsulatorTabBuildEvent(list);
        ModEvents.post(event);

        return event.getItems().stream().map(entry -> new ItemStack(entry.getKey())).toList();
    }
}