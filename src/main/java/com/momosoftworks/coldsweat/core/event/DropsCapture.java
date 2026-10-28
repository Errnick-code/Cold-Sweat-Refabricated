package com.momosoftworks.coldsweat.core.event;

import net.minecraft.world.entity.item.ItemEntity;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class DropsCapture
{
    private static final ThreadLocal<List<ItemEntity>> CAPTURED = new ThreadLocal<>();

    private DropsCapture()
    {}

    public static void start()
    {   CAPTURED.set(new ArrayList<>());
    }

    public static void capture(ItemEntity item)
    {
        List<ItemEntity> captured = CAPTURED.get();
        if (captured != null)
        {   captured.add(item);
        }
    }

    @Nullable
    public static List<ItemEntity> stop()
    {
        List<ItemEntity> captured = CAPTURED.get();
        CAPTURED.remove();
        return captured;
    }
}
