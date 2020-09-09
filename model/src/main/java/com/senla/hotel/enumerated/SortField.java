package com.senla.hotel.enumerated;

public enum SortField {

    DEFAULT("id"),
    STARS("stars"),
    ACCOMMODATION("accommodation"),
    PRICE("price"),
    NAME("name"),
    CHECK_OUT_DATE("check_out");

    private String fieldName;

    SortField(final String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
