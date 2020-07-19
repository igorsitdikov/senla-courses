package com.senla.hotel;

import com.senla.hotel.configuration.interfaces.ObjectConfigurator;
import com.senla.hotel.exception.ConverterNotExistsException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
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
            System.err.println("Instantiation! " + e);
        } catch (final IllegalAccessException e) {
            System.err.println("Illegal access! " + e);
        } catch (final InvocationTargetException e) {
            System.err.println("Invocation target! " + e);
        } catch (final NoSuchMethodException e) {
            System.err.println("No such method! " + e);
        }
        configure(t);
        return t;
    }

    private <T> void configure(final T t) {
        configurators.forEach(objectConfigurator -> {
            try {
                objectConfigurator.configure(t, context);
            } catch (final IllegalAccessException e) {
                System.err.println("Illegal access! " + e);
            } catch (final ConverterNotExistsException e) {
                System.err.println("No converter for this type! " + e);
            }
        });
    }

    private <T> T create(final Class<T> implClass)
        throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }
}




