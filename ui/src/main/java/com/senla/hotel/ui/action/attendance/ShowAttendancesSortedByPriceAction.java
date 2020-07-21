package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowAttendancesSortedByPriceAction implements Action {
    private AttendanceController attendanceController;

    public ShowAttendancesSortedByPriceAction(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute() {
        List<Attendance> attendances = attendanceController.showAttendancesSortedByPrice();
        Printer.show(attendances);
    }
}
