package com.senla.hotel.ui.action.admin;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;

public class ExportHistoryAction implements Action {
    private HotelController hotelController;

    public ExportHistoryAction(final HotelController hotelController) {
        this.hotelController = hotelController;
    }

    @Override
    public void execute() {
        try {
            hotelController.exportHistories();
        } catch (final PersistException e) {
            System.err.printf("Could not export histories to csv %s%n", e.getMessage());
        }
    }
}
