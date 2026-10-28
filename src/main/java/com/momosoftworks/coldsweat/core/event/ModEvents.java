package com.momosoftworks.coldsweat.core.event;

public final class ModEvents
{
    public static void register(Class<?> eventClass)
    {   EventBus.register(eventClass);
    }

    public static void register(Object listener)
    {   EventBus.register(listener);
    }

    public static void unregister(Object listener)
    {   EventBus.unregister(listener);
    }

    public static void registerAnnotatedClasses(String packageName)
    {   EventBus.registerAnnotatedClasses(packageName);
    }

    public static void registerAnnotatedClasses(String packageName, boolean includeClient)
    {   EventBus.registerAnnotatedClasses(packageName, includeClient);
    }

    public static <T> T post(T event)
    {   return EventBus.post(event);
    }
}