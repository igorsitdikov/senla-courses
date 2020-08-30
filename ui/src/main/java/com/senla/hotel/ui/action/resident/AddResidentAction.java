package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.Gender;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;

import java.util.Scanner;

import static com.senla.hotel.ui.utils.EnumConverter.integerToGender;

public class AddResidentAction implements Action {

    private final ResidentController residentController;

    public AddResidentAction(final ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {

        final Scanner scanner = new Scanner(System.in);
        try {
            final String firstName = InputDataReader.getStringInput(scanner, "Input first name...");
            final String lastName = InputDataReader.getStringInput(scanner, "Input last name...");
            final String phone = InputDataReader.getStringInput(scanner, "Input phone...");
            final Boolean vip = InputDataReader
                    .getIntegerInput(scanner, "Input Resident vip 1 or 2, where" +
                            "1 - FALSE, " +
                            "2 - TRUE", 2) != 1;
            final Gender gender = integerToGender(InputDataReader
                    .getIntegerInput(scanner, "Input Resident gender 1 or 2, where" +
                            "1 - MALE, " +
                            "2 - FEMALE", 2));
            residentController.createResident(new Resident(firstName, lastName, gender, vip, phone, null));
        } catch (final Exception e) {
            System.err.printf("Failed to add a Resident! Input valid parameters! %s%n%n", e);
        }
    }
}
