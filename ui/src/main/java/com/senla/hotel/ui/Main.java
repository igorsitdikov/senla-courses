package com.senla.hotel.ui;

import com.senla.hotel.AppContext;
import com.senla.hotel.Application;
import com.senla.hotel.properties.PropertyLoader;
import com.senla.hotel.ui.menu.MenuController;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Map<Class<?>, Class<?>> CLASS_MAP = new HashMap<>();

    public static void main(String[] args) throws Exception {
        configure();
        AppContext context = Application.run("com.senla.hotel", CLASS_MAP);
        MenuController menuController = context.getObject(MenuController.class);
        menuController.run();
    }

    public static void configure() {
        PropertyLoader propertyLoader = new PropertyLoader();
        propertyLoader.init("instance.properties");
        for (final String name : propertyLoader.getProperties().stringPropertyNames()) {
            try {
                CLASS_MAP.put(Class.forName(name), Class.forName(propertyLoader.getProperty(name)));
            } catch (ClassNotFoundException e) {
                System.err.printf("Class not found %s%n", e.getMessage());
            }
        }
    }
}
