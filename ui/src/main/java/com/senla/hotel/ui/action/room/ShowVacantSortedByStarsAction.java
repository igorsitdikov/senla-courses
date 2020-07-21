package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowVacantSortedByStarsAction implements Action {
    private RoomController roomController;

    public ShowVacantSortedByStarsAction(RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        List<Room> rooms = roomController.showVacantRoomsSortedByStars();
        Printer.show(rooms);
    }
}
