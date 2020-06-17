package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.RoomController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;

import java.math.BigDecimal;
import java.util.Scanner;

public class CreateAttendanceAction implements IAction {

    Stars integerToStar(Integer input) {
        Stars roomStar = null;
        switch (input) {
            case 1:
                roomStar = Stars.JUNIOR_SUIT;
                break;
            case 2:
                roomStar = Stars.SUIT;
                break;
            case 3:
                roomStar = Stars.DE_LUX;
                break;
            case 4:
                roomStar = Stars.DUPLEX;
                break;
            case 5:
                roomStar = Stars.FAMILY_ROOM;
                break;
            case 6:
                roomStar = Stars.STUDIO;
                break;
            case 7:
                roomStar = Stars.STANDARD;
                break;
            case 8:
                roomStar = Stars.APARTMENT;
                break;
            case 9:
                roomStar = Stars.HONEYMOON_ROOM;
                break;
            default:
                System.out.println("Wrong input. Please, input a number from 1 to 9.");
        }
        return roomStar;
    }

    RoomStatus integerToStatus(Integer input) {
        RoomStatus roomStatus = null;
        switch (input) {
            case 1:
                roomStatus = RoomStatus.VACANT;
                break;
            case 2:
                roomStatus = RoomStatus.OCCUPIED;
                break;
            case 3:
                roomStatus = RoomStatus.REPAIR;
                break;
            default:
                System.out.println("Wrong input. Please, input a number from 1 to 3. Try again!");
        }
        return roomStatus;
    }

    Accommodation integerToAccommodation(Integer input) {
        Accommodation accommodation = null;
        switch (input) {
            case 1:
                accommodation = Accommodation.SGL;
                break;
            case 2:
                accommodation = Accommodation.SGL_CHD;
                break;
            case 3:
                accommodation = Accommodation.SGL_2_CHD;
                break;
            case 4:
                accommodation = Accommodation.DBL;
                break;
            case 5:
                accommodation = Accommodation.DBL_EXB;
                break;
            case 6:
                accommodation = Accommodation.DBL_CHD;
                break;
            case 7:
                accommodation = Accommodation.DBL_EXB_CHD;
                break;
            case 8:
                accommodation = Accommodation.TRPL;
                break;
            case 9:
                accommodation = Accommodation.TRPL_CHD;
                break;
            case 10:
                accommodation = Accommodation.TRPL_2_CHLD;
                break;
            default:
                System.out.println("Wrong input. Please, input a number from 1 to 10.%n");
        }
        return accommodation;
    }

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            Integer roomNumber =
                InputDataReader
                    .getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
            Stars stars =
                integerToStar(
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
            Accommodation accommodation =
                integerToAccommodation(
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
            BigDecimal dailyPrice =
                BigDecimal.valueOf(InputDataReader.getDoubleInput(scanner, "Input the Room daily price..."));
            RoomStatus status = integerToStatus(
                InputDataReader
                    .getIntegerInput(scanner,
                                     "Input the Room status, where\n " +
                                     "\t1 = VACANT, " +
                                     "\t2 = OCCUPIED, " +
                                     "\t3 = REPAIR...",
                                     RoomStatus.values().length));
            RoomController.getInstance().addRoom(new Room(roomNumber, stars, accommodation, dailyPrice, status));

        } catch (Exception e) {
            System.out.println(String.format("Failed to add a Room! Input valid parameters! %s", e));
        }
    }
}
