package com.senla.bulletin_board.enumerated;

public enum SortBulletin {

    DEFAULT("id"),
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
