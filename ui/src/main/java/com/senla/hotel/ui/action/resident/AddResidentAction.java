package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.enumerated.Gender;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static com.senla.hotel.ui.utils.EnumConverter.integerToGender;

public class AddResidentAction implements Action {

    private static final Logger logger = LogManager.getLogger(AddResidentAction.class);

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
            residentController.createResident(new ResidentDto(null, firstName, lastName, gender, vip, phone, null));
        } catch (final Exception e) {
            logger.error("Failed to add a Resident! Input valid parameters! {}", e.getMessage());
        }
    }
}
