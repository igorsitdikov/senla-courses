package com.senla.enumerated;

public enum Type {
    STRING(String.class),
    BOOLEAN(Boolean.class),
    INTEGER(Integer.class),
    DOUBLE(Double.class);
    private Class<?> aClass;

    Type(final Class<?> aClass) {
        this.aClass = aClass;
    }

    public Class<?> getaClass() {
        return aClass;
    }
}
