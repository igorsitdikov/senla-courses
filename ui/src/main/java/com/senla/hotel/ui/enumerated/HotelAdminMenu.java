package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.IMenuEnum;

public enum HotelAdminMenu implements IMenuEnum {
    CHECK_IN(1, "Check in resident"),
    CHECK_OUT(2, "Check out resident"),
    CALCULATE_BILL(3, "Calculate resident's bill");

    private int id;
    private String title;

    HotelAdminMenu(final int id, final String title) {
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
