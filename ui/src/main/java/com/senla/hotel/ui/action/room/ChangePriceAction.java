package com.senla.hotel.ui.action.room;

import com.senla.annotation.Autowired;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;

import java.math.BigDecimal;
import java.util.Scanner;

public class ChangePriceAction implements Action {
    @Autowired
    private RoomController roomController;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        Integer roomNumber =
            InputDataReader
                .getIntegerInput(scanner, "Input the Room number...", Integer.MAX_VALUE);
        BigDecimal dailyPrice =
            BigDecimal.valueOf(InputDataReader.getDoubleInput(scanner, "Input new Room daily price..."));

        try {
            roomController.changePrice(roomNumber, dailyPrice);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
