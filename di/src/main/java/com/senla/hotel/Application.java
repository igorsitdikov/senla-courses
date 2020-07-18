package com.senla.hotel;

import com.senla.hotel.configuration.ConfigImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Application {
    public static AppContext run(String packageToScan, Map<Class<?>, Class<?>> ifc2ImplClass)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ConfigImpl config = new ConfigImpl(packageToScan, ifc2ImplClass);
        AppContext context = new AppContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);
        context.setFactory(objectFactory);
        return context;
    }
}
