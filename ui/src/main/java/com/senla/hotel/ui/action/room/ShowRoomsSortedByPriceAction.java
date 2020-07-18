package com.senla.hotel.ui.action.room;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.MenuItem;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

@MenuItem
public class ShowRoomsSortedByPriceAction implements IAction {
    @Autowired
    private RoomController roomController;

    @Override
    public void execute() {
        List<Room> rooms = roomController.showAllRoomsSortedByPrice();
        Printer.show(rooms);
    }
}
