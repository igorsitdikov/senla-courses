package com.senla.hotel.ui.action.admin;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CheckInAction implements Action {
    private ResidentController residentController;
    private HotelController hotelController;
    private RoomController roomController;

    public CheckInAction(ResidentController residentController, HotelController hotelController, RoomController roomController) {
        this.residentController = residentController;
        this.hotelController = hotelController;
        this.roomController = roomController;
    }

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            final List<Room> rooms = roomController.showAllRooms();
            Printer.show(rooms, "room");
            Integer roomId = InputDataReader
                    .getIntegerInput(scanner, "Input Room id...", rooms.size());

            final List<Resident> residents = residentController.showResidents();
            Printer.show(residents, "resident");
            Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.size());
            LocalDate checkInDate = InputDataReader.getLocalDateInput(scanner, "Input check-in date...");
            LocalDate checkOutDate = InputDataReader.getLocalDateInput(scanner, "Input check-out date...");

            hotelController
                    .checkIn(residents.get(residentId - 1), rooms.get(roomId - 1), checkInDate, checkOutDate);
        } catch (Exception e) {
            System.err.println(String.format("Failed to check-in! Input valid parameters! %s", e));
        }
    }
}
