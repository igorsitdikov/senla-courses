package com.senla.bulletin_board.enumerated;

public enum SortBulletin {

    AVERAGE("averageVote"),
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
