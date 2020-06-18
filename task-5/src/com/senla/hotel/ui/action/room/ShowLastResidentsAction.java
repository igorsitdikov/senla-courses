package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ShowLastResidentsAction implements IAction {

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        try {
            final Room[] rooms = RoomController.getInstance().showAllRooms();
            List<Room> roomsList = Arrays.asList(rooms);
            System.out.println("Choose room: ");
            IntStream.range(0, roomsList.size())
                    .forEach(index -> String.format("%d. %s", (index + 1), roomsList.get(index)));

            Integer roomId = InputDataReader
                    .getIntegerInput(scanner, "Input Room id...", roomsList.size() - 1);
            Integer number = InputDataReader
                    .getIntegerInput(scanner, "Input number of last...", Integer.MAX_VALUE);

            Resident[] residents = RoomController.getInstance().showLastResidents(roomsList.get(roomId - 1), number);
            List<Resident> entities = Arrays.asList(residents);
            Printer.show(entities);
        } catch (Exception e) {
            System.out.println(String.format("Failed to show Resident in Room! Input valid parameters! %s", e));
        }
    }
}
