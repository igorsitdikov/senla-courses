package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowRoomsSortedByPriceAction implements Action {
    private RoomController roomController;

    public ShowRoomsSortedByPriceAction(RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        List<Room> rooms = roomController.showAllRoomsSortedByPrice();
        Printer.show(rooms);
    }
}
