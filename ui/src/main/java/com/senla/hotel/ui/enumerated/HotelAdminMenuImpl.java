package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.MenuEnum;

public enum HotelAdminMenuImpl implements MenuEnum {
    CHECK_IN(1, "Check in resident"),
    CHECK_OUT(2, "Check out resident"),
    CALCULATE_BILL(3, "Calculate resident's bill");

    private int id;
    private String title;

    HotelAdminMenuImpl(final int id, final String title) {
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
