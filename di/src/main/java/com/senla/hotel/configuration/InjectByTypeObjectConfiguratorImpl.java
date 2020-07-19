package com.senla.hotel.configuration;

import com.senla.annotaion.Autowired;
import com.senla.hotel.AppContext;
import com.senla.hotel.configuration.interfaces.ObjectConfigurator;

import java.lang.reflect.Field;

public class InjectByTypeObjectConfiguratorImpl implements ObjectConfigurator {
    @Override
    public void configure(Object t, AppContext context) throws IllegalAccessException {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                field.set(t, object);
            }
        }
    }
}
