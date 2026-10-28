package com.momosoftworks.coldsweat.core.init;

import java.util.function.Supplier;

public final class Ref<T>
{
    private final Supplier<T> supplier;
    private T value;

    public Ref(T value)
    {   this.supplier = () -> value;
    }

    public Ref(Supplier<T> supplier)
    {   this.supplier = supplier;
    }

    public T value()
    {
        if (value == null)
        {   value = supplier.get();
        }
        return value;
    }

    public T get()
    {   return value();
    }
}
