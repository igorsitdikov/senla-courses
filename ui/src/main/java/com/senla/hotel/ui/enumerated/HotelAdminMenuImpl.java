package com.senla.hotel.ui.enumerated;

import com.senla.hotel.ui.enumerated.interfaces.MenuEnum;

public enum HotelAdminMenuImpl implements MenuEnum {
    CHECK_IN(1, "Check in resident"),
    CHECK_OUT(2, "Check out resident"),
    CALCULATE_BILL(3, "Calculate resident's bill"),
    EXPORT_HISTORIES_TO_CSV(4, "Export histories to csv"),
    IMPORT_HISTORIES_FROM_CSV(5, "Import histories from csv");

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
