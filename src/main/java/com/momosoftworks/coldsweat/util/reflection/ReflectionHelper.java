package com.momosoftworks.coldsweat.util.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectionHelper
{
    private ReflectionHelper() {}

    public static Field findField(Class<?> clazz, String name)
    {
        try
        {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        }
        catch (NoSuchFieldException e)
        {
            throw new ReflectionException("Unable to find field '" + name + "' in " + clazz.getName(), e);
        }
        catch (RuntimeException e)
        {
            throw new ReflectionException("Unable to access field '" + name + "' in " + clazz.getName(), e);
        }
    }

    public static Method findMethod(Class<?> clazz, String name, Class<?>... args)
    {
        try
        {
            Method method = clazz.getDeclaredMethod(name, args);
            method.setAccessible(true);
            return method;
        }
        catch (NoSuchMethodException e)
        {
            throw new ReflectionException("Unable to find method '" + name + "' in " + clazz.getName(), e);
        }
        catch (RuntimeException e)
        {
            throw new ReflectionException("Unable to access method '" + name + "' in " + clazz.getName(), e);
        }
    }

    public static class ReflectionException extends RuntimeException
    {
        public ReflectionException(String message, Throwable cause)
        {   super(message, cause);
        }
    }
}