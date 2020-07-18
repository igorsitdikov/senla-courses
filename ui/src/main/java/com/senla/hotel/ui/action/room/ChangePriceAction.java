package com.senla.hotel.ui.action.room;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.MenuItem;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;

import java.math.BigDecimal;
import java.util.Scanner;

@MenuItem
public class ChangePriceAction implements IAction {
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
