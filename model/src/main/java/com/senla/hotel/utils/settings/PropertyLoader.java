package com.senla.hotel.utils.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static PropertyLoader propertyLoader;
    private Properties properties = new Properties();
    private static final String PROPERTY_FILE = "model/src/main/resources/config.properties";

    private PropertyLoader() {
        init();
    }

    public static PropertyLoader getInstance() {
        if (propertyLoader == null) {
            propertyLoader = new PropertyLoader();
        }
        return propertyLoader;
    }

    private void init() {
        try (final InputStream input = new FileInputStream(PROPERTY_FILE)) {
            properties.load(input);
        } catch (final IOException e) {
            System.err.println(String.format("File not found %s %s%n", PROPERTY_FILE, e));
        }
    }

    public String getProperty(final String key) {
        return properties.getProperty(key);
    }
}
