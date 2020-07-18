package com.senla.hotel.ui.action.admin;

import com.senla.anntotaion.Autowired;
import com.senla.hotel.controller.HotelController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;
import java.util.Scanner;

public class CalculateBillAction implements IAction {
    @Autowired
    private ResidentController residentController;
    @Autowired
    private HotelController hotelController;

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            final List<Resident> residents = residentController.showResidents();
            Printer.show(residents, "resident");
            Integer residentId = InputDataReader
                .getIntegerInput(scanner, "Input Resident id...", residents.size() - 1);

            hotelController.calculateBill(residents.get(residentId - 1));
        } catch (Exception e) {
            System.err.println(String.format("Failed to check-in! Input valid parameters! %s", e));
        }
    }
}
