package com.senla.hotel.ui.action.resident;

import com.senla.annotation.Autowired;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.ui.interfaces.Action;

public class CountResidentsAction implements Action {
    @Autowired
    private ResidentController residentController;

    @Override
    public void execute() {
        final int residents = residentController.showCountResidents();
        System.out.println(String.format("Total residents: %d", residents));
    }
}
