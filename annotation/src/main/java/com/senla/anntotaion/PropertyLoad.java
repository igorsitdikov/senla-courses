package com.senla.anntotaion;

import com.senla.enumerated.Type;

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
