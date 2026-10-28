package com.momosoftworks.coldsweat.api.temperature.effect;

import com.momosoftworks.coldsweat.data.codec.util.IntegerBounds;

public class TempEffectType<T extends TempEffect>
{
    private final Constructor<T> constructor;
    private final TempEffect.Side side;

    public TempEffectType(Constructor<T> constructor)
    {   this(TempEffect.Side.COMMON, constructor);
    }

    public TempEffectType(TempEffect.Side side, Constructor<T> constructor)
    {   this.side = side;
        this.constructor = constructor;
    }

    public T create(TempEffectType<T> type, IntegerBounds range)
    {   return constructor.create(type, range);
    }

    public TempEffect.Side getSide()
    {   return side;
    }

    @FunctionalInterface
    public interface Constructor<T extends TempEffect>
    {   T create(TempEffectType<T>type, IntegerBounds range);
    }
}
