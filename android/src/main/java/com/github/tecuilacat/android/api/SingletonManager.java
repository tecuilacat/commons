package com.github.tecuilacat.android.api;

import java.util.HashMap;
import java.util.Map;

public class SingletonManager {

    private SingletonManager() { }

    private static final Map<Class<?>, Object> services = new HashMap<>();

    @SuppressWarnings("java:S112")
    public static <T> T getService(Class<T> serviceClass) {
        if (!services.containsKey(serviceClass)) {
            try {
                services.put(serviceClass, serviceClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new RuntimeException("Service konnte nicht erstellt werden: " + serviceClass.getName(), e);
            }
        }
        return serviceClass.cast(services.get(serviceClass));
    }

    @SuppressWarnings({"java:S3011", "java:S112"})
    public static void injectServices(Object target) {
        Class<?> targetClass = target.getClass();

        for (var field : targetClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Singleton.class)) {
                Class<?> serviceClass = field.getType();
                Object service = getService(serviceClass);

                field.setAccessible(true);
                try {
                    field.set(target, service);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Fehler bei der Service-Injektion", e);
                }
            }
        }
    }

}
