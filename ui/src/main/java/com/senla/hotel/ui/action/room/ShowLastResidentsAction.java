package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;
import java.util.Scanner;

public class ShowLastResidentsAction implements Action {

    private final RoomController roomController;

    public ShowLastResidentsAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {
        final Scanner scanner = new Scanner(System.in);
        try {
            final List<Room> rooms = roomController.showAllRooms(SortField.DEFAULT);
            Printer.show(rooms, "room");

            final Integer roomId = InputDataReader
                .getIntegerInput(scanner, "Input Room id...", rooms.size() - 1);
            final Integer number = InputDataReader
                .getIntegerInput(scanner, "Input number of last...", Integer.MAX_VALUE);

            final List<Resident> residents = roomController.showLastResidents(rooms.get(roomId - 1), number);
            Printer.show(residents);
        } catch (final Exception e) {
            System.err.printf("Failed to show Resident in Room! Input valid parameters! %s%n%n", e);
        }
    }
}
