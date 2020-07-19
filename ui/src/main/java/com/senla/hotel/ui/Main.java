package com.senla.hotel.ui;

import com.senla.hotel.AppContext;
import com.senla.hotel.Application;
import com.senla.hotel.properties.PropertyLoader;
import com.senla.hotel.ui.menu.MenuController;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Map<Class<?>, Class<?>> classMap = new HashMap<>();

    public static void main(final String[] args) throws Exception {
        configure();
        final AppContext context = Application.run("com.senla.hotel", classMap);
        final MenuController menuController = context.getObject(MenuController.class);
        menuController.run();
    }

    public static void configure() {
        final PropertyLoader propertyLoader = new PropertyLoader();
        propertyLoader.init("instance.properties");
        for (final String name : propertyLoader.getProperties().stringPropertyNames()) {
            try {
                classMap.put(Class.forName(name), Class.forName(propertyLoader.getProperty(name)));
            } catch (final ClassNotFoundException e) {
                System.err.printf("Class not found %s%n", e.getMessage());
            }
        }
    }
}
