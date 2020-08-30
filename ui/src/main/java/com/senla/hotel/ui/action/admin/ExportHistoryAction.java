package com.senla.hotel.ui.action.admin;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExportHistoryAction implements Action {

    private static final Logger logger = LogManager.getLogger(ExportHistoryAction.class);

    private HotelController hotelController;

    public ExportHistoryAction(final HotelController hotelController) {
        this.hotelController = hotelController;
    }

    @Override
    public void execute() {
        try {
            hotelController.exportHistories();
        } catch (final PersistException e) {
            logger.error("Could not export histories to csv {}", e.getMessage());
        }
    }
}
