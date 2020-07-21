package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.ui.interfaces.Action;

public class CountResidentsAction implements Action {
    private ResidentController residentController;

    public CountResidentsAction(ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {
        final int residents = residentController.showCountResidents();
        System.out.println(String.format("Total residents: %d", residents));
    }
}
