package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.IMenuEnum;

public enum ShowResidentsMenu implements IMenuEnum {
    ALL_RESIDENTS(1, "Show all residents"),
    SORT_BY_NAME(2, "Show all residents sorted by name"),
    SORT_BY_CHECK_OUT(3, "Show all residents sorted by check out date");

    private int id;
    private String title;

    ShowResidentsMenu(final int id, final String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
