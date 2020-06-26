package com.senla.hotel.utils.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static PropertyLoader propertyLoader;
    private Properties properties = new Properties();
    private String filename = "resources/config.properties";

    private PropertyLoader() {
        init();
    }

    private PropertyLoader(final String filename) {
        this.filename = filename;
        init();
    }

    public static PropertyLoader getInstance() {
        if (propertyLoader == null) {
            propertyLoader = new PropertyLoader();
        }
        return propertyLoader;
    }

    public static PropertyLoader getInstance(final String filename) {
        if (propertyLoader == null) {
            propertyLoader = new PropertyLoader(filename);
        }
        return propertyLoader;
    }

    private void init() {
        try (final InputStream input = new FileInputStream(this.filename)) {
            properties.load(input);
        } catch (final IOException e) {
            System.err.println(String.format("File not found %s %s%n", filename, e));
        }
    }

    public String getProperty(final String key) {
        return properties.getProperty(key);
    }
}
