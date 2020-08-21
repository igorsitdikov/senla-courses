package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowAttendancesAction implements Action {
    private AttendanceController attendanceController;

    public ShowAttendancesAction(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute() {
        List<Attendance> attendances = null;
        try {
            attendances = attendanceController.showAttendances();
        } catch (PersistException e) {
            System.err.println(e.getMessage());
        }
        Printer.show(attendances);
    }
}
