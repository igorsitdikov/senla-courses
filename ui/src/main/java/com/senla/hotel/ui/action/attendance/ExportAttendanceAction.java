package com.senla.hotel.ui.action.attendance;

import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;

public class ExportAttendanceAction implements Action {
    private AttendanceController attendanceController;

    public ExportAttendanceAction(final AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    @Override
    public void execute() {
        try {
            attendanceController.exportAttendances();
        } catch (final PersistException e) {
            System.err.printf("Could not export attendances to csv %s%n", e.getMessage());
        }
    }
}
