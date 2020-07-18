package com.senla.hotel.configuration;

import com.senla.hotel.configuration.interfaces.Config;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class ConfigImpl implements Config {
    private Reflections scanner;
    private Map<Class<?>, Class<?>> ifc2ImplClass;

    public ConfigImpl(String packageToScan, Map<Class<?>, Class<?>> ifc2ImplClass) {
        this.ifc2ImplClass = ifc2ImplClass;
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<T> getImplClass(Class<T> ifc) {
        return (Class<T>) ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            if (classes.size() != 1) {
                throw new RuntimeException(ifc + " has 0 or more than one impl please update your config");
            }
            return classes.iterator().next();
        });

    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }
}












