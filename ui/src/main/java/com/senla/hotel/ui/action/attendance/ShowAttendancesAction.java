package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.Printer;

import java.util.List;

public class ShowAttendancesAction implements Action {
    private final AttendanceController attendanceController;

    public ShowAttendancesAction(final AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute() {
        try {
            final List<Attendance> attendances = attendanceController.showAttendances(SortField.DEFAULT);
            Printer.show(attendances);
        } catch (final PersistException e) {
            System.err.println(e.getMessage());
        }
    }
}
