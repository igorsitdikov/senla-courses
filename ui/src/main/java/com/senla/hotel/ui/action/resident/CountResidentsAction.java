package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;

public class CountResidentsAction implements Action {
    private final ResidentController residentController;

    public CountResidentsAction(final ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {
        try {
            final int residents = residentController.showCountResidents();
            System.out.printf("Total residents: %d%n%n", residents);
        } catch (final PersistException e) {
            e.printStackTrace();
        }
    }
}
