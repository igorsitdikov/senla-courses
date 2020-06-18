package com.senla.hotel.ui.action.admin;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CalculateBillAction implements IAction {

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            final Resident[] residents = ResidentController.getInstance().showResidents();
            List<Resident> residentsList = Arrays.asList(residents);
            System.out.println("Choose resident: ");
            IntStream.range(0, residentsList.size())
                    .forEach(index -> String.format("%d. %s", (index + 1), residentsList.get(index)));
            Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.length - 1);

            HotelController.getInstance().calculateBill(residentsList.get(residentId - 1));
        } catch (Exception e) {
            System.out.println(String.format("Failed to check-in! Input valid parameters! %s", e));
        }
    }
}
