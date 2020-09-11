package com.senla.hotel;

import com.senla.hotel.configuration.interfaces.ObjectConfigurator;
import com.senla.hotel.exception.ConverterNotExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {

    private static final Logger logger = LogManager.getLogger(ObjectFactory.class);

    private final AppContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();

    public ObjectFactory(final AppContext context)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.context = context;
        for (final Class<?> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add((ObjectConfigurator) aClass.getDeclaredConstructor().newInstance());
        }
    }

    public <T> T createObject(final Class<T> implClass) {
        T t = null;
        try {
            t = create(implClass);
        } catch (final InstantiationException e) {
            logger.error("Instantiation! {}", e.getMessage());
        } catch (final IllegalAccessException e) {
            logger.error("Illegal access! {}", e.getMessage());
        } catch (final InvocationTargetException e) {
            logger.error("Invocation target! {}", e.getMessage());
        } catch (final NoSuchMethodException e) {
            logger.error("No such method! {}", e.getMessage());
        }
        configure(t);
        return t;
    }

    private <T> void configure(final T t) {
        configurators.forEach(objectConfigurator -> {
            try {
                objectConfigurator.configure(t, context);
            } catch (final IllegalAccessException e) {
                logger.error("Illegal access! {}", e.getMessage());
            } catch (final ConverterNotExistsException e) {
                logger.error("No converter for this type! {}", e.getMessage());
            }
        });
    }

    private <T> T create(final Class<T> implClass)
        throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Constructor<?>[] constructors = implClass.getConstructors();
        int lengthConstructors = constructors.length;
        if (lengthConstructors > 1 || constructors[0].getParameterCount() > 0) {
            for (Constructor c : constructors) {
                Class[] parameterTypes = c.getParameterTypes();
                if (parameterTypes.length > 0) {
                    Object[] implParameters = new Object[parameterTypes.length];
                    for (int i = 0; i < implParameters.length; i++) {
                        final Object object = context.getObject(parameterTypes[i]);
                        implParameters[i] = object;
                    }
                    try {
                        return (T) c.newInstance(implParameters);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return implClass.getDeclaredConstructor().newInstance();
    }
}
