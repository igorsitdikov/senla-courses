package com.senla.hotel.controller;

import com.senla.hotel.service.HotelAdminService;
import com.senla.hotel.service.interfaces.IHotelAdminService;

public final class HotelController {
    private static HotelController hotelController;
    private static IHotelAdminService hotelAdminService;

    public HotelController() {
        hotelAdminService = new HotelAdminService();
    }

    public static HotelController getInstance() {
        if (hotelController == null) {
            hotelController = new HotelController();
        }
        return hotelController;
    }

}
