package com.senla.hotel;

import com.senla.hotel.configuration.ConfigImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Application {
    public static AppContext run(final String packageToScan, final Map<Class<?>, Class<?>> ifc2ImplClass)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        final ConfigImpl config = new ConfigImpl(packageToScan, ifc2ImplClass);
        final AppContext context = new AppContext(config);
        final ObjectFactory objectFactory = new ObjectFactory(context);
        context.setFactory(objectFactory);
        return context;
    }
}
