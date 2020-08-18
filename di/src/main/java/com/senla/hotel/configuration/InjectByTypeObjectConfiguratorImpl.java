package com.senla.hotel.configuration;

import com.senla.hotel.AppContext;
import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.configuration.interfaces.ObjectConfigurator;
import com.senla.hotel.utils.Connector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class InjectByTypeObjectConfiguratorImpl implements ObjectConfigurator {
    @Override
    public void configure(final Object t, final AppContext context) throws IllegalAccessException {
        for (final Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);

                Constructor<?>[] constructors = context.getObject(field.getType()).getClass().getConstructors();
                int lengthConstructors = constructors.length;
                if (lengthConstructors > 1) {
                    for (Constructor c : constructors) {
                        Class[] parameterTypes = c.getParameterTypes();
                        for (Class<?> clazz : parameterTypes) {
                            if (clazz.equals(Connector.class)) {
                                final Object object = context.getObject(clazz);
                                Object ob = null;
                                try {
                                    ob = c.newInstance(object);
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                                field.set(t, ob);
                            }
                        }
                    }
                } else {
                    final Object object = context.getObject(field.getType());
                    field.set(t, object);
                }
            }
        }
    }
}
