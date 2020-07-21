package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowAttendancesSortedByNameAction implements Action {
    private AttendanceController attendanceController;

    public ShowAttendancesSortedByNameAction(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute() {
        List<Attendance> attendances = attendanceController.showAttendancesSortedByName();
        Printer.show(attendances);
    }
}
