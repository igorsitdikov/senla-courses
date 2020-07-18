package com.senla.hotel.ui.action.resident;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.MenuItem;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.Gender;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;

import java.util.Scanner;

@MenuItem
public class AddResidentAction implements IAction {
    @Autowired
    private ResidentController residentController;

    private Gender integerToGender(Integer input) {
        Gender gender = null;
        switch (input) {
            case 1:
                gender = Gender.MALE;
                break;
            case 2:
                gender = Gender.FEMALE;
                break;
            default:
                System.out.println("Wrong input. Please, input a number from 1 to 2. Try again!");
        }
        return gender;
    }

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            String firstName = InputDataReader.getStringInput(scanner);
            String lastName = InputDataReader.getStringInput(scanner);
            String phone = InputDataReader.getStringInput(scanner);
            Boolean vip = InputDataReader.getIntegerInput(scanner, "Input Resident vip 1 or 2, where" +
                                                                   "1 - FALSE, " +
                                                                   "2 - TRUE", 2) != 1;
            Gender gender =
                integerToGender(InputDataReader.getIntegerInput(scanner, "Input Resident gender 1 or 2, where" +
                                                                         "1 - MALE, " +
                                                                         "2 - FEMALE", 2));
            residentController.createResident(new Resident(firstName, lastName, gender, vip, phone, null));
        } catch (Exception e) {
            System.err.println(String.format("Failed to add a Resident! Input valid parameters! %s", e));
        }
    }
}
