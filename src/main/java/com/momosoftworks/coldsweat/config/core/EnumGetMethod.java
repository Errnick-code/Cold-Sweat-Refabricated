package com.momosoftworks.coldsweat.config.core;

public enum EnumGetMethod
{
    NAME,
    NAME_IGNORECASE,
    ORDINAL;

    public static EnumGetMethod ordinalMethod()
    {
        return ORDINAL;
    }

    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> T get(Object value, Class<T> clazz)
    {
        if (value == null)
        {
            return null;
        }
        if (clazz.isInstance(value))
        {
            return (T) value;
        }
        if (this == ORDINAL)
        {
            if (value instanceof Number number)
            {
                T[] constants = clazz.getEnumConstants();
                int index = number.intValue();
                return index >= 0 && index < constants.length ? constants[index] : null;
            }
            return null;
        }
        String name = value.toString().trim();
        if (name.isEmpty())
        {
            return null;
        }
        for (T constant : clazz.getEnumConstants())
        {
            if (this == NAME && constant.name().equals(name))
            {
                return constant;
            }
            if (this != NAME && constant.name().equalsIgnoreCase(name))
            {
                return constant;
            }
        }
        return null;
    }
}