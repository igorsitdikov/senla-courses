package com.senla.hotel.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static Properties properties;
    private static final String PROPERTY_FILE = "model/src/main/resources/application.properties";

    public PropertyLoader() {
        System.out.println("created " + PropertyLoader.class);
        properties = new Properties();
        init();
    }

    private void init() {
        try (final InputStream input = new FileInputStream(PROPERTY_FILE)) {
            properties.load(input);
        } catch (final IOException e) {
            System.err.println(String.format("File not found %s %s%n", PROPERTY_FILE, e));
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
