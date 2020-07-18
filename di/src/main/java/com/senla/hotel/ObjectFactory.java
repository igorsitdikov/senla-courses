package com.senla.hotel;

import com.senla.hotel.exception.ConverterNotExistsException;
import com.senla.hotel.configuration.interfaces.ObjectConfigurator;

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
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        configure(t);
        return t;
    }

    private <T> void configure(T t) {
        configurators.forEach(objectConfigurator -> {
            try {
                objectConfigurator.configure(t, context);
            } catch (IllegalAccessException | ConverterNotExistsException e) {
                e.printStackTrace();
            }
        });
    }

    private <T> T create(Class<T> implClass)
        throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }
}




