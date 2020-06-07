package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Resident;

import java.math.BigDecimal;
import java.util.Comparator;

public interface IResidentService {

    void add(Resident resident);

    Resident[] sortResidents(final Resident[] residents, final Comparator<Resident> comparator);

    void showResidents(final Resident[] residents);

    BigDecimal calculateBill(Long id);

    void showAttendances(Long id);

    void sortResidentAttendancesByDate();

    Resident[] getResidents();
}
