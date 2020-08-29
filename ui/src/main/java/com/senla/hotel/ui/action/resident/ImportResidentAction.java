package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;

public class ImportResidentAction implements Action {

    private ResidentController residentController;

    public ImportResidentAction(final ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {
        try {
            residentController.importResidents();
        } catch (final PersistException e) {
            System.err.printf("Could not import residents from csv %s%n", e.getMessage());
        }
    }
}
