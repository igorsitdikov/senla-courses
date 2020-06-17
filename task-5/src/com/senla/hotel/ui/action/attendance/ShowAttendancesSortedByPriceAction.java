package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.ui.interfaces.IAction;
import com.senla.hotel.ui.utils.Printer;

import java.util.Arrays;
import java.util.List;

public class ShowAttendancesSortedByPriceAction implements IAction {
    @Override
    public void execute() {
        Attendance[] attendances = AttendanceController.getInstance().showAttendancesSortedByPrice();
        List<Attendance> entities = Arrays.asList(attendances);
        Printer.show(entities);
    }
}
