package com.senla.hotel.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private static final Logger logger = LogManager.getLogger(PropertyLoader.class);

    private static Properties properties;
    private static final String PATH_TO_PROPERTIES = "model/src/main/resources/";

    public PropertyLoader() {
        properties = new Properties();
    }

    public void init(final String filename) {
        try (InputStream input = new FileInputStream(PATH_TO_PROPERTIES + filename)) {
            properties.load(input);
        } catch (final IOException e) {
            logger.error("File not found {} {}", filename, e);
        }
    }

    public String getProperty(final String key) {
        return properties.getProperty(key);
    }

    public Properties getProperties() {
        return properties;
    }
}
