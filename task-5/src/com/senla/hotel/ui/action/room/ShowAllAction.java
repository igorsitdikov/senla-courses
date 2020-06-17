package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.Printer;

import java.util.Arrays;
import java.util.List;

public class ShowAllAction implements IAction {

    @Override
    public void execute() {
        Room[] rooms = RoomController.getInstance().showAllRooms();
        List<Room> entities = Arrays.asList(rooms);
        Printer.show(entities);
    }
}
