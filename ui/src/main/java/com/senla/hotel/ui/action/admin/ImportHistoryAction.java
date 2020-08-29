package com.senla.hotel.ui.action.admin;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;

public class ImportHistoryAction implements Action {

    private HotelController hotelController;

    public ImportHistoryAction(final HotelController hotelController) {
        this.hotelController = hotelController;
    }

    @Override
    public void execute() {
        try {
            hotelController.importHistories();
        } catch (final PersistException e) {
            System.err.printf("Could not import histories from csv %s%n", e.getMessage());
        }
    }
}
