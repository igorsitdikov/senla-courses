package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.EnumConverter;
import com.senla.hotel.ui.utils.InputDataReader;

import java.math.BigDecimal;
import java.util.Scanner;

public class AddRoomAction implements Action {
    private final RoomController roomController;

    public AddRoomAction(final RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void execute() {

        final Scanner scanner = new Scanner(System.in);
        try {
            final Integer roomNumber =
                InputDataReader
                    .getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
            final Stars stars =
                EnumConverter.integerToStar(
                    InputDataReader
                        .getIntegerInput(scanner, "Input the Room rate from 1 to 9, where" +
                                                  "1 - JUNIOR SUIT, " +
                                                  "2 - SUIT, " +
                                                  "3 - DE LUX, " +
                                                  "4 - DUPLEX, " +
                                                  "5 - FAMILY ROOM, " +
                                                  "6 - STUDIO, " +
                                                  "7 - STANDARD, " +
                                                  "8 - APARTMENT, " +
                                                  "9 - HONEYMOON ROOM...", Stars.values().length));
            final Accommodation accommodation =
                EnumConverter.integerToAccommodation(
                    InputDataReader
                        .getIntegerInput(scanner, "Input the Room accommodation, where" +
                                                  "1 - SINGLE RESIDENT, " +
                                                  "2 - SINGLE RESIDENT WITH 1 CHILD, " +
                                                  "3 - SINGLE RESIDENT WITH 2 CHILDREN, " +
                                                  "4 - DOUBLE RESIDENTS, " +
                                                  "5 - DOUBLE RESIDENTS WITH EXTRA BAD, " +
                                                  "6 - DOUBLE RESIDENTS WITH 1 CHILD, " +
                                                  "7 - DOUBLE RESIDENTS WITH 1 CHILD AND EXTRA BAD, " +
                                                  "8 - TRIPLE RESIDENTS, " +
                                                  "9 - TRIPLE RESIDENTS WITH 1 CHILDREN, " +
                                                  "10 - TRIPLE RESIDENTS WITH 2 CHILDREN...",
                                         Accommodation.values().length));
            final BigDecimal dailyPrice =
                BigDecimal.valueOf(InputDataReader.getDoubleInput(scanner, "Input the Room daily price..."));
            final RoomStatus status = EnumConverter.integerToStatus(
                InputDataReader
                    .getIntegerInput(scanner,
                                     "Input the Room status, where\n " +
                                     "\t1 = VACANT, " +
                                     "\t2 = OCCUPIED, " +
                                     "\t3 = REPAIR...",
                                     RoomStatus.values().length));
            roomController.addRoom(new Room(roomNumber, stars, accommodation, dailyPrice, status));

        } catch (final Exception e) {
            System.err.printf("Failed to add a Room! Input valid parameters! %s%n%n", e);
        }
    }
}
