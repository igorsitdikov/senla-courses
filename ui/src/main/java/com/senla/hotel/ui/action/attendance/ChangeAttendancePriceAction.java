package com.senla.hotel.ui.action.attendance;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.MenuItem;
import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.InputDataReader;

import java.math.BigDecimal;
import java.util.Scanner;

@MenuItem
public class ChangeAttendancePriceAction implements IAction {
    @Autowired
    private AttendanceController attendanceController;

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        try {
            String name = InputDataReader.getStringInput(scanner);
            BigDecimal dailyPrice =
                BigDecimal.valueOf(InputDataReader.getDoubleInput(scanner, "Input the Attendance daily price..."));
            attendanceController.changePrice(name, dailyPrice);
        } catch (Exception e) {
            System.err.println(String.format("Failed to change price a Attendance! Input valid parameters! %s", e));
        }
    }
}
