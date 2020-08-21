package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowRoomsSortedByStarsAction implements Action {
    private final RoomController roomController;

    public ShowRoomsSortedByStarsAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        try {
            final List<Room> rooms  = roomController.showAllRooms(SortField.STARS);
            Printer.show(rooms);
        } catch (final PersistException e) {
            System.err.println(e.getMessage());
        }
    }
}
