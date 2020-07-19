package com.senla.hotel;

import com.senla.annotation.Singleton;
import com.senla.hotel.configuration.interfaces.Config;

import java.util.HashMap;
import java.util.Map;

public class AppContext {
    private ObjectFactory factory;
    private Map<Class<?>, Object> cache = new HashMap<>();
    private Config config;


    public AppContext(final Config config) {
        this.config = config;
    }

    public <T> T getObject(final Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }

        final T t = factory.createObject(implClass);

        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, t);
        }

        return t;
    }

    public Config getConfig() {
        return config;
    }

    public void setFactory(final ObjectFactory factory) {
        this.factory = factory;
    }

}
