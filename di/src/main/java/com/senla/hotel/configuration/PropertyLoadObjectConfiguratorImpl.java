package com.senla.hotel.configuration;

import com.senla.hotel.AppContext;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.configuration.interfaces.ObjectConfigurator;
import com.senla.hotel.enumerated.Type;
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
    public void configure(final Object t, final AppContext context)
        throws IllegalAccessException, ConverterNotExistsException {

        for (final Field field : t.getClass().getDeclaredFields()) {
            final PropertyLoad annotation = field.getAnnotation(PropertyLoad.class);
            if (annotation != null) {
                propertyLoader.init(annotation.configName());
                field.setAccessible(true);
                final String propertyName = getPropertyName(annotation, getDefaultPropertyName(t, field));
                final String value = propertyLoader.getProperty(propertyName);
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
