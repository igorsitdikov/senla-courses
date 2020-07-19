package com.senla.enumerated;

public enum Type {
    STRING(String.class),
    BOOLEAN(Boolean.class),
    INTEGER(Integer.class),
    DOUBLE(Double.class);
    private Class<?> type;

    Type(final Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }
}
