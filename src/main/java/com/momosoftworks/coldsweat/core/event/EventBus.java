package com.momosoftworks.coldsweat.core.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class EventBus
{
    private record Handler(Object target, Method method, EventPriority priority, boolean receiveCanceled) {}

    private static final List<Class<?>> REGISTERED_CLASSES = new ArrayList<>();
    private static final List<Handler> HANDLERS = new CopyOnWriteArrayList<>();

    public static void register(Class<?> eventClass)
    {
        if (REGISTERED_CLASSES.contains(eventClass)) return;

        for (Method method : collectMethods(eventClass))
        {
            if (java.lang.reflect.Modifier.isStatic(method.getModifiers()))
            {   HANDLERS.add(new Handler(null, method, priority(method), receiveCanceled(method)));
            }
        }
        REGISTERED_CLASSES.add(eventClass);
    }

    public static void register(Object listener)
    {
        if (listener == null) return;

        for (Method method : collectMethods(listener.getClass()))
        {
            if (!java.lang.reflect.Modifier.isStatic(method.getModifiers()))
            {   HANDLERS.add(new Handler(listener, method, priority(method), receiveCanceled(method)));
            }
        }
    }

    public static void unregister(Object listener)
    {   HANDLERS.removeIf(handler -> handler.target() == listener);
    }

    public static void registerAnnotatedClasses(String packageName)
    {   registerAnnotatedClasses(packageName, false);
    }

    public static void registerAnnotatedClasses(String packageName, boolean includeClient)
    {
        for (Class<?> eventClass : scan(packageName, includeClient))
        {   register(eventClass);
        }
    }

    private static List<Class<?>> scan(String packageName)
    {   return scan(packageName, false);
    }

    private static List<Class<?>> scan(String packageName, boolean includeClient)
    {
        List<Class<?>> classes = new ArrayList<>();
        ClassLoader loader = EventBus.class.getClassLoader();
        String path = packageName.replace('.', '/');

        net.fabricmc.loader.api.FabricLoader.getInstance()
                .getModContainer(com.momosoftworks.coldsweat.ColdSweat.MOD_ID)
                .flatMap(container -> container.findPath(path))
                .ifPresent(root ->
                {
                    try (java.util.stream.Stream<java.nio.file.Path> files = java.nio.file.Files.walk(root))
                    {
                        files.filter(file -> file.getFileName().toString().endsWith(".class"))
                             .forEach(file ->
                             {
                                 String relative = root.relativize(file).toString().replace('\\', '/');
                                 String binaryName = packageName + "."
                                         + relative.substring(0, relative.length() - ".class".length()).replace('/', '.');
                                 addClass(loader, binaryName, classes, includeClient);
                             });
                    }
                    catch (java.io.IOException e)
                    {   com.momosoftworks.coldsweat.ColdSweat.LOGGER.error("Failed to scan package " + packageName, e);
                    }
                });

        if (classes.isEmpty())
        {   scanClasspath(packageName, loader, classes, includeClient);
        }

        return classes;
    }

    private static void scanClasspath(String packageName, ClassLoader loader, List<Class<?>> classes, boolean includeClient)
    {
        String path = packageName.replace('.', '/');

        try
        {   java.util.Enumeration<java.net.URL> resources = loader.getResources(path);

            while (resources.hasMoreElements())
            {   java.net.URL resource = resources.nextElement();

                if ("file".equals(resource.getProtocol()))
                {   java.nio.file.Path directory = java.nio.file.Paths.get(resource.toURI());
                    try (java.util.stream.Stream<java.nio.file.Path> files = java.nio.file.Files.walk(directory))
                    {
                        files.filter(file -> file.toString().endsWith(".class")).forEach(file ->
                        {
                            String relative = directory.relativize(file).toString().replace('\\', '/');
                            String binaryName = packageName + "." + relative.substring(0, relative.length() - ".class".length()).replace('/', '.');
                            addClass(loader, binaryName, classes, includeClient);
                        });
                    }
                }
                else if ("jar".equals(resource.getProtocol()))
                {   java.net.URLConnection connection = resource.openConnection();
                    if (!(connection instanceof java.net.JarURLConnection jarConnection)) continue;

                    java.util.jar.JarFile jar = jarConnection.getJarFile();
                    java.util.Enumeration<java.util.jar.JarEntry> entries = jar.entries();

                    while (entries.hasMoreElements())
                    {
                        String name = entries.nextElement().getName();
                        if (!name.startsWith(path + "/") || !name.endsWith(".class")) continue;

                        addClass(loader, name.substring(0, name.length() - ".class".length()).replace('/', '.'), classes, includeClient);
                    }
                }
            }
        }
        catch (Exception e)
        {   com.momosoftworks.coldsweat.ColdSweat.LOGGER.error("Failed to scan classpath for package " + packageName, e);
        }
    }

    private static void addClass(ClassLoader loader, String binaryName, List<Class<?>> classes)
    {   addClass(loader, binaryName, classes, false);
    }

    private static void addClass(ClassLoader loader, String binaryName, List<Class<?>> classes, boolean includeClient)
    {
        String simpleName = binaryName.substring(binaryName.lastIndexOf('.') + 1);
        if (simpleName.isEmpty()) return;

        int nestedStart = simpleName.lastIndexOf('$');
        if (nestedStart >= 0 && simpleName.substring(nestedStart + 1).chars().allMatch(Character::isDigit)) return;

        String packageName = binaryName.contains(".") ? binaryName.substring(0, binaryName.lastIndexOf('.')) : "";
        if ((!includeClient && packageName.contains(".client")) || packageName.contains(".mixin")) return;

        try
        {   Class<?> type = Class.forName(binaryName, false, loader);

            if (type.isInterface() || type.isEnum() || java.lang.reflect.Modifier.isAbstract(type.getModifiers())) return;

            boolean annotated = false;
            for (Method method : type.getDeclaredMethods())
            {   if (method.getAnnotation(SubscribeEvent.class) != null)
            {   annotated = true;
                break;
            }
            }
            if (annotated) classes.add(type);
        }
        catch (ClassNotFoundException | LinkageError | RuntimeException e)
        {   }
        catch (Throwable e)
        {   }
    }

    private static List<Method> collectMethods(Class<?> type)
    {
        List<Method> methods = new ArrayList<>();
        for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass())
        {
            for (Method method : current.getDeclaredMethods())
            {
                if (method.getAnnotation(SubscribeEvent.class) == null) continue;

                method.setAccessible(true);
                methods.add(method);
            }
        }
        methods.sort(Comparator.comparingInt(method -> priority(method).ordinal()));
        return methods;
    }

    private static EventPriority priority(Method method)
    {   return method.getAnnotation(SubscribeEvent.class).priority();
    }

    private static boolean receiveCanceled(Method method)
    {   return method.getAnnotation(SubscribeEvent.class).receiveCanceled();
    }

    public static <T> T post(T event)
    {
        if (HANDLERS.isEmpty()) return event;

        for (Handler handler : new ArrayList<>(HANDLERS))
        {
            Class<?>[] parameterTypes = handler.method().getParameterTypes();
            if (parameterTypes.length != 1 || !parameterTypes[0].isInstance(event)) continue;

            if (event instanceof Cancellable cancellable && cancellable.isCanceled() && !handler.receiveCanceled()) continue;

            try
            {   handler.method().invoke(handler.target(), event);
            }
            catch (IllegalAccessException e)
            {   throw new IllegalStateException("Event handler " + handler.method() + " is not accessible", e);
            }
            catch (InvocationTargetException e)
            {   throw new IllegalStateException("Event handler " + handler.method() + " failed", e.getTargetException());
            }

            if (event instanceof Cancellable cancellable && cancellable.isCanceled()) break;
        }
        return event;
    }

    public static void clear()
    {
        HANDLERS.clear();
        REGISTERED_CLASSES.clear();
    }
}