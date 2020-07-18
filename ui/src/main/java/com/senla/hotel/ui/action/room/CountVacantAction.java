package com.senla.hotel.ui.action.room;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.MenuItem;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.ui.interfaces.IAction;

@MenuItem
public class CountVacantAction implements IAction {
    @Autowired
    private RoomController roomController;

    @Override
    public void execute() {
        final int vacantRooms = roomController.countVacantRooms();
        System.out.println(String.format("Total vacant rooms: %d", vacantRooms));
    }
}
