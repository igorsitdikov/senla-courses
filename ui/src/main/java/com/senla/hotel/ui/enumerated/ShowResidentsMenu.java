package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.IMenuEnum;

public enum ShowResidentsMenu implements IMenuEnum {
    ALL_RESIDENTS(1, "Show all residents"),
    SORT_BY_NAME(2, "Show all residents sorted by name"),
    SORT_BY_CHECK_OUT(3, "Show all residents sorted by check out date");

    private int id;
    private String name;

    ShowResidentsMenu(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
