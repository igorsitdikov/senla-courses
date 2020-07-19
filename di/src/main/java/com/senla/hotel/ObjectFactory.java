package com.senla.hotel;

import com.senla.hotel.configuration.interfaces.ObjectConfigurator;
import com.senla.hotel.exception.ConverterNotExistsException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private final AppContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();

    public ObjectFactory(AppContext context)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.context = context;
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner()
            .getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    public <T> T createObject(Class<T> implClass) {
        T t = null;
        try {
            t = create(implClass);
        } catch (InstantiationException e) {
            System.err.println("Instantiation! " + e);
        } catch (IllegalAccessException e) {
            System.err.println("Illegal access! " + e);
        } catch (InvocationTargetException e) {
            System.err.println("Invocation target! " + e);
        } catch (NoSuchMethodException e) {
            System.err.println("No such method! " + e);
        }
        configure(t);
        return t;
    }

    private <T> void configure(T t) {
        configurators.forEach(objectConfigurator -> {
            try {
                objectConfigurator.configure(t, context);
            } catch (IllegalAccessException e) {
                System.err.println("Illegal access! " + e);
            } catch (ConverterNotExistsException e) {
                System.err.println("No converter for this type! " + e);
            }
        });
    }

    private <T> T create(Class<T> implClass)
        throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }
}




