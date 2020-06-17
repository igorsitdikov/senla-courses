package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.Printer;

import java.util.Arrays;
import java.util.List;

public class ShowAllSortedByAccommodationAction implements IAction {
    @Override
    public void execute() {
        Room[] rooms = HotelController.getInstance().showAllRoomsSortedByAccommodation();
        List<Room> entities = Arrays.asList(rooms);
        Printer.show(entities);
    }
}
