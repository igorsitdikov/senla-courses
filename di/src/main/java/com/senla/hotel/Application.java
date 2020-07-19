package com.senla.hotel;

import com.senla.hotel.configuration.ConfigImpl;

import java.lang.reflect.InvocationTargetException;

public class Application {
    public static AppContext run(final String packageToScan)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        final ConfigImpl config = new ConfigImpl(packageToScan);
        final AppContext context = new AppContext(config);
        final ObjectFactory objectFactory = new ObjectFactory(context);
        context.setFactory(objectFactory);
        return context;
    }
}
