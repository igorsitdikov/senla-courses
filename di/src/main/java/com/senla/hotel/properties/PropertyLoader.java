package com.senla.hotel.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private static Properties properties;
    private static final String PATH_TO_PROPERTIES = "model/src/main/resources/";

    public PropertyLoader() {
        properties = new Properties();
    }

    public void init(final String filename) {
        try (InputStream input = new FileInputStream(PATH_TO_PROPERTIES + filename)) {
            properties.load(input);
        } catch (final IOException e) {
            System.err.println(String.format("File not found %s %s%n", filename, e));
        }
    }

    public String getProperty(final String key) {
        return properties.getProperty(key);
    }

    public Properties getProperties() {
        return properties;
    }
}
