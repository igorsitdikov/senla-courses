package com.senla.hotel.configuration.interfaces;

import com.senla.hotel.AppContext;
import com.senla.hotel.exception.ConverterNotExistsException;

public interface ObjectConfigurator {

    void configure(Object impl, AppContext context) throws IllegalAccessException, ConverterNotExistsException;
}
