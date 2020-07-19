package com.senla.hotel.configuration;

import com.senla.annotation.Singleton;
import com.senla.hotel.Scanner;
import com.senla.hotel.configuration.interfaces.Config;
import org.reflections.Reflections;

import java.util.Map;

public class ConfigImpl implements Config {
    private final Reflections scanner;
    private Map<Class<?>, Class<?>> ifc2ImplClass;

    public ConfigImpl(final String packageToScan) {
        this.ifc2ImplClass = Scanner.findInfImplInPackage(packageToScan, Singleton.class);
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Class<T> getImplClass(final Class<T> ifc) {
        return (Class<T>) ifc2ImplClass.get(ifc);
    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }
}












