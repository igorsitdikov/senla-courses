package com.senla.hotel.ui.action.resident;

import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.dto.AmountDto;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.ui.interfaces.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CountResidentsAction implements Action {

    private static final Logger logger = LogManager.getLogger(CountResidentsAction.class);

    private final ResidentController residentController;

    public CountResidentsAction(final ResidentController residentController) {
        this.residentController = residentController;
    }

    @Override
    public void execute() {
        try {
            final AmountDto residents = residentController.showCountResidents();
            logger.info("Total residents: {}", residents.getAmount());
        } catch (final PersistException e) {
            logger.error(e.getMessage());
        }
    }
}
