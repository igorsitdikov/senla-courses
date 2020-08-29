package com.senla.hotel.configuration.interfaces;

import com.senla.hotel.Scanner;

public interface Config {

    <T> Class<? extends T> getImplClass(Class<T> ifc);

    Scanner getScanner();
}
