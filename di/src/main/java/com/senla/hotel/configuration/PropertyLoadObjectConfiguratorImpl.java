package com.senla.hotel.configuration;

import com.senla.anntotaion.PropertyLoad;
import com.senla.enumerated.Type;
import com.senla.hotel.AppContext;
import com.senla.hotel.configuration.interfaces.ObjectConfigurator;
import com.senla.hotel.exception.ConverterNotExistsException;
import com.senla.hotel.properties.PropertyLoader;
import com.senla.hotel.utils.ObjectConverter;

import java.lang.reflect.Field;

public class PropertyLoadObjectConfiguratorImpl implements ObjectConfigurator {
    private final PropertyLoader propertyLoader;

    public PropertyLoadObjectConfiguratorImpl() {
        propertyLoader = new PropertyLoader();
    }

    @Override
    public void configure(Object t, AppContext context)
        throws IllegalAccessException, ConverterNotExistsException {

        for (Field field : t.getClass().getDeclaredFields()) {
            PropertyLoad annotation = field.getAnnotation(PropertyLoad.class);
            if (annotation != null) {
                propertyLoader.init(annotation.configName());
                field.setAccessible(true);
                String propertyName = getPropertyName(annotation, getDefaultPropertyName(t, field));
                String value = propertyLoader.getProperty(propertyName);
                if (annotation.type() == Type.STRING) {
                    field.set(t, value);
                } else {
                    final Object convertedValue = ObjectConverter.convert(value, annotation.type().getType());
                    field.set(t, convertedValue);
                }
            }
        }
    }

    private String getDefaultPropertyName(final Object object, final Field field) {
        return String.format("%s.%s", object.getClass().getSimpleName(), field.getName());
    }

    private String getPropertyName(final PropertyLoad annotation, final String defaultName) {
        if (annotation.propertyName().isEmpty()) {
            return defaultName;
        }
        return annotation.propertyName();
    }
}
