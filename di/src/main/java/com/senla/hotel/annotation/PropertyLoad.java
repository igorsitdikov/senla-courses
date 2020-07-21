package com.senla.hotel.annotation;

import com.senla.hotel.enumerated.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PropertyLoad {
    String configName() default "application.properties";

    String propertyName() default "";

    Type type() default Type.STRING;
}
