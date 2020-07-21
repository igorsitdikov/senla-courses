package com.senla.hotel.ui.action.room;

import com.senla.annotation.Autowired;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowVacantSortedByStarsAction implements Action {
    @Autowired
    private RoomController roomController;

    @Override
    public void execute() {
        List<Room> rooms = roomController.showVacantRoomsSortedByStars();
        Printer.show(rooms);
    }
}
