package com.senla.hotel.ui.action.room;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ShowDetailsAction implements IAction {

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            Integer roomNumber = InputDataReader.getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
            Room room = HotelController.getInstance().showRoomDetails(roomNumber);
            Printer.show(room);
        } catch (Exception e) {
            System.out.println(String.format("Failed to add a Room! Input valid parameters! %s", e));
        }

    }
}
