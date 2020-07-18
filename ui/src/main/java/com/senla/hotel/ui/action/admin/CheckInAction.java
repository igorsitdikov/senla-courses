package com.senla.hotel.ui.action.admin;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.MenuItem;
import com.senla.hotel.controller.HotelController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@MenuItem
public class CheckInAction implements IAction {
    @Autowired
    private ResidentController residentController;
    @Autowired
    private HotelController hotelController;
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

            final List<Resident> residents = residentController.showResidents();
            Printer.show(residents, "resident");
            Integer residentId = InputDataReader
                .getIntegerInput(scanner, "Input Resident id...", residents.size() - 1);
            LocalDate checkInDate = InputDataReader.getLocalDateInput(scanner, "Input check-in date...");
            LocalDate checkOutDate = InputDataReader.getLocalDateInput(scanner, "Input check-out date...");

            hotelController
                .checkIn(residents.get(residentId - 1), rooms.get(roomId - 1), checkInDate, checkOutDate);
        } catch (Exception e) {
            System.err.println(String.format("Failed to check-in! Input valid parameters! %s", e));
        }
    }
}