package com.senla.hotel.ui.action.admin;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CheckInAction implements Action {

    private final ResidentController residentController;
    private final HotelController hotelController;
    private final RoomController roomController;

    public CheckInAction(final ResidentController residentController, final HotelController hotelController, final RoomController roomController) {
        this.residentController = residentController;
        this.hotelController = hotelController;
        this.roomController = roomController;
    }

    @Override
    public void execute() {

        final Scanner scanner = new Scanner(System.in);
        try {
            final List<Room> rooms = roomController.showAllRooms(SortField.DEFAULT);
            Printer.show(rooms, "room");
            final Integer roomId = InputDataReader
                    .getIntegerInput(scanner, "Input Room id...", rooms.size());

            final List<Resident> residents = residentController.showResidents(SortField.DEFAULT);
            Printer.show(residents, "resident");
            final Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.size());
            final LocalDate checkInDate = InputDataReader.getLocalDateInput(scanner, "Input check-in date...");
            final LocalDate checkOutDate = InputDataReader.getLocalDateInput(scanner, "Input check-out date...");

            hotelController
                    .checkIn(residents.get(residentId - 1), rooms.get(roomId - 1), checkInDate, checkOutDate);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
