package com.senla.enumerated;

public enum Storage {
    HISTORIES("histories"),
    RESIDENTS("residents"),
    ROOMS("rooms"),
    ATTENDANCES("attendances");

    private String value;

    Storage(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
