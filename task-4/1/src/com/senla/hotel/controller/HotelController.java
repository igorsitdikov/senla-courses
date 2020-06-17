package com.senla.hotel.controller;

import com.senla.hotel.entity.Room;
import com.senla.hotel.service.HotelAdminService;
import com.senla.hotel.service.interfaces.IHotelAdminService;

public final class HotelController {
    private static IHotelAdminService hotelAdminService;

    public static IHotelAdminService getInstance() {
        if (hotelAdminService == null) {
            hotelAdminService = new HotelAdminService();
        }
        return hotelAdminService;
    }

    public void addRoom(final Room room) {
        hotelAdminService.addRoom(room);
    }

    public Room[] showAllRooms() {
        return hotelAdminService.showAllRooms();
    }
    public Room[] showVacantRooms() {
        return hotelAdminService.showVacantRooms();
    }

}
