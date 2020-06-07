package com.senla.hotel.service;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.repository.ResidentRepository;
import com.senla.hotel.service.interfaces.IResidentService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

public class ResidentService implements IResidentService {
    private ResidentRepository residentRepository = new ResidentRepository();

    @Override
    public void add(final Resident resident) {
        residentRepository.add(resident);
    }

    @Override
    public Resident[] sortResidents(final Resident[] residents, final Comparator<Resident> comparator) {
        Arrays.sort(residents, comparator);
        return residents;
    }

    @Override
    public void showResidents(final Resident[] residents) {
        for (int i = 0; i < residents.length; i++) {
            System.out.println(residents[i].toString());
        }
    }

    @Override
    public BigDecimal calculateBill(final Long id) {
        return null;
    }

    @Override
    public void showAttendances(final Long id) {

    }

    @Override
    public void sortResidentAttendancesByDate() {

    }

    @Override
    public Resident[] getResidents() {
        return residentRepository.getResidents();
    }
}
