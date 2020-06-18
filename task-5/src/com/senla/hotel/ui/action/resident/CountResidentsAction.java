package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.ui.interfaces.IAction;

public class CountResidentsAction implements IAction {

    @Override
    public void execute() {
        final int residents = ResidentController.getInstance().showCountResidents();
        System.out.println(String.format("Total residents: %d", residents));
    }
}
