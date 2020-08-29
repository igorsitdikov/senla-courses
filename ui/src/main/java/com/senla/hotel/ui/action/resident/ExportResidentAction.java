package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;

public class ExportResidentAction implements Action {

    private ResidentController residentController;

    public ExportResidentAction(final ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {
        try {
            residentController.exportResidents();
        } catch (final PersistException e) {
            System.err.printf("Could not export residents to csv %s%n", e.getMessage());
        }
    }
}
