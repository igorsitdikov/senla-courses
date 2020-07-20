package com.senla.hotel.configuration;

import com.senla.annotation.Singleton;
import com.senla.hotel.Scanner;
import com.senla.hotel.configuration.interfaces.Config;

import java.util.Map;
import java.util.Set;

public class ConfigImpl implements Config {
    private final Scanner scanner;
    private Map<Class<?>, Class<?>> ifc2ImplClass;

    public ConfigImpl(final String packageToScan) {
        this.scanner = new Scanner(packageToScan);
        this.ifc2ImplClass = scanner.findInfImplInPackage(packageToScan, Singleton.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Class<T> getImplClass(final Class<T> ifc) {
        return (Class<T>) ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            final Set<Class<?>> classes = scanner.getSubTypesOf(ifc);
            if (classes.size() != 1) {
                throw new RuntimeException(ifc + " has 0 or more than one impl please update your config");
            }
            return classes.iterator().next();
        });
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }
}












