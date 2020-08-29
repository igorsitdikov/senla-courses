package com.senla.hotel.configuration;

import com.senla.hotel.AppContext;
import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.configuration.interfaces.ObjectConfigurator;

import java.lang.reflect.Field;

public class InjectByTypeObjectConfiguratorImpl implements ObjectConfigurator {

    @Override
    public void configure(final Object t, final AppContext context) throws IllegalAccessException {
        for (final Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                final Object object = context.getObject(field.getType());
                field.set(t, object);
            }
        }
    }
}
