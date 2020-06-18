package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowRoomsAction implements IAction {

    @Override
    public void execute() {
        List<Room> rooms = RoomController.getInstance().showAllRooms();
        Printer.show(rooms);
    }
}
