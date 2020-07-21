package com.senla.hotel.ui.action.room;

import com.senla.annotation.Autowired;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;
import java.util.Scanner;

public class ShowLastResidentsAction implements Action {
    @Autowired
    private RoomController roomController;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        try {
            final List<Room> rooms = roomController.showAllRooms();
            Printer.show(rooms, "room");

            Integer roomId = InputDataReader
                .getIntegerInput(scanner, "Input Room id...", rooms.size() - 1);
            Integer number = InputDataReader
                .getIntegerInput(scanner, "Input number of last...", Integer.MAX_VALUE);

            List<Resident> residents = roomController.showLastResidents(rooms.get(roomId - 1), number);
            Printer.show(residents);
        } catch (Exception e) {
            System.err.println(String.format("Failed to show Resident in Room! Input valid parameters! %s", e));
        }
    }
}
