package com.senla.hotel.ui.action.resident;

import com.senla.anntotaion.Autowired;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.ui.interfaces.IAction;

public class CountResidentsAction implements IAction {
    @Autowired
    private ResidentController residentController;

    @Override
    public void execute() {
        final int residents = residentController.showCountResidents();
        System.out.println(String.format("Total residents: %d", residents));
    }
}
