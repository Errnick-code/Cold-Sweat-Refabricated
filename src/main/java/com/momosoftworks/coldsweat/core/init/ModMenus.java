package com.momosoftworks.coldsweat.core.init;

import com.momosoftworks.coldsweat.ColdSweat;
import com.momosoftworks.coldsweat.common.container.BoilerContainer;
import com.momosoftworks.coldsweat.common.container.HearthContainer;
import com.momosoftworks.coldsweat.common.container.IceboxContainer;
import com.momosoftworks.coldsweat.common.container.PosMenuProvider;
import com.momosoftworks.coldsweat.common.container.SewingContainer;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenus
{
    public static final Ref<MenuType<BoilerContainer>> BOILER_CONTAINER_TYPE = menu("boiler", BoilerContainer::new);
    public static final Ref<MenuType<IceboxContainer>> ICEBOX_CONTAINER_TYPE = menu("ice_box", IceboxContainer::new);
    public static final Ref<MenuType<SewingContainer>> SEWING_CONTAINER_TYPE = menu("sewing_table", (id, inventory, pos) -> new SewingContainer(id, inventory));
    public static final Ref<MenuType<HearthContainer>> HEARTH_CONTAINER_TYPE = menu("hearth", HearthContainer::new);

    private static <T extends AbstractContainerMenu> Ref<MenuType<T>> menu(String name, PosMenuProvider.PosMenuFactory<T> factory)
    {
        MenuType<T> type = new ExtendedMenuType<>(factory::create, BlockPos.STREAM_CODEC);
        Registry.register(BuiltInRegistries.MENU, ColdSweat.createKey(name), type);
        return new Ref<>(type);
    }
}
