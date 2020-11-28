package com.senla.bulletinboard.enumerated;

public enum SortBulletin {

    AVERAGE("vote"),
    DATE("createdAt"),
    AUTHOR("firstName"),
    PRICE("price");

    private String field;

    SortBulletin(final String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

}
