package com.senla.hotel.ui.action.admin;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.ui.exceptions.ListEntitiesIsEmptyException;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CheckOutAction implements IAction {

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            final List<Resident> residents = ResidentController.getInstance().showResidents();
            Printer.show(residents, "resident");

            Integer residentId = InputDataReader
                .getIntegerInput(scanner, "Input Resident id...", residents.size() - 1);
            LocalDate checkOutDate = InputDataReader.getLocalDateInput(scanner, "Input check-out date...");

            HotelController.getInstance().checkOut(residents.get(residentId - 1), checkOutDate);
        } catch (ListEntitiesIsEmptyException ex) {
            System.err.println(ex);
        }catch (Exception e) {
            System.err.println(String.format("Failed to check-out! Input valid parameters! %n%s%n", e));
        }
    }
}
