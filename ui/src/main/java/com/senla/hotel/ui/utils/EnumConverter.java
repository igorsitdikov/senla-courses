package com.senla.hotel.ui.utils;

import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;

public class EnumConverter {
    public static RoomStatus integerToStatus(Integer input) {
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

    public static Accommodation integerToAccommodation(Integer input) {
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
                accommodation = Accommodation.TRPL_2_CHD;
                break;
            default:
                System.out.println("Wrong input. Please, input a number from 1 to 10.%n");
        }
        return accommodation;
    }

    public static Stars integerToStar(Integer input) {
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

}