package com.senla.hotel.ui.action.admin;

import com.senla.hotel.controller.HotelController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.dto.CheckOutDto;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.ui.exceptions.ListEntitiesIsEmptyException;
import com.senla.hotel.ui.interfaces.Action;
import com.senla.hotel.ui.utils.InputDataReader;
import com.senla.hotel.ui.utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CheckOutAction implements Action {

    private static final Logger logger = LogManager.getLogger(CheckOutAction.class);

    private final ResidentController residentController;
    private final HotelController hotelController;

    public CheckOutAction(final ResidentController residentController, final HotelController hotelController) {
        this.residentController = residentController;
        this.hotelController = hotelController;
    }

    @Override
    public void execute() {

        final Scanner scanner = new Scanner(System.in);
        try {
            final List<ResidentDto> residents = residentController.showResidents(SortField.DEFAULT);
            Printer.show(residents, "resident");

            final Integer residentId = InputDataReader
                    .getIntegerInput(scanner, "Input Resident id...", residents.size());
            final LocalDate checkOutDate = InputDataReader.getLocalDateInput(scanner, "Input check-out date...");

            hotelController.checkOut(new CheckOutDto(residents.get(residentId - 1), checkOutDate));
        } catch (final ListEntitiesIsEmptyException ex) {
            logger.error(ex.getMessage());
        } catch (final Exception e) {
            logger.error("Failed to check-out! Input valid parameters! {}", e.getMessage());
        }
    }
}
