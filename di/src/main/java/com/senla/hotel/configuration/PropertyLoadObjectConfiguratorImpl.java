package com.senla.hotel.configuration;

import com.senla.anntotaion.PropertyLoad;
import com.senla.hotel.AppContext;
import com.senla.hotel.configuration.interfaces.ObjectConfigurator;
import com.senla.hotel.exception.ConverterNotExistsException;
import com.senla.hotel.properties.PropertyLoader;
import com.senla.hotel.utils.ObjectConverter;

import java.lang.reflect.Field;
import java.util.Properties;

public class PropertyLoadObjectConfiguratorImpl implements ObjectConfigurator {

    private final Properties propertiesMap;

    public PropertyLoadObjectConfiguratorImpl() {
        PropertyLoader propertyLoader = new PropertyLoader();
        propertiesMap = propertyLoader.getProperties();
    }

    @Override
    public void configure(Object t, AppContext context)
        throws IllegalAccessException, ConverterNotExistsException {

        for (Field field : t.getClass().getDeclaredFields()) {
            PropertyLoad annotation = field.getAnnotation(PropertyLoad.class);
            if (annotation != null) {
                field.setAccessible(true);
                String value = propertiesMap.getProperty(annotation.value());
                final Object convertedValue = ObjectConverter.convert(value, field.getType());
                field.set(t, convertedValue);
            }
        }
    }
}
