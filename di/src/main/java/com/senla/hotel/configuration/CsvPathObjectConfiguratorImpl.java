package com.senla.hotel.configuration;

import com.senla.anntotaion.CsvPath;
import com.senla.hotel.AppContext;
import com.senla.hotel.configuration.interfaces.ObjectConfigurator;
import com.senla.hotel.properties.PropertyLoader;

import java.lang.reflect.Field;
import java.util.Properties;

public class CsvPathObjectConfiguratorImpl implements ObjectConfigurator {

    private final Properties propertiesMap;

    public CsvPathObjectConfiguratorImpl() {
        PropertyLoader propertyLoader = new PropertyLoader();
        propertiesMap = propertyLoader.getProperties();
    }

    @Override
    public void configure(Object t, AppContext context) throws IllegalAccessException {

        for (Field field : t.getClass().getDeclaredFields()) {
            CsvPath annotation = field.getAnnotation(CsvPath.class);
            if (annotation != null) {
                field.setAccessible(true);
                String value = propertiesMap.getProperty(annotation.value().getValue());
                field.set(t, value);
            }
        }
    }
}
