package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;

import java.util.Arrays;

public class ResidentRepository extends ARepository {

    private static Resident[] residents = new Resident[0];

    public ResidentRepository() {
    }

    public ResidentRepository(final Resident[] residents) {
        this.residents = residents;
    }

    public Resident[] castArray(final AEntity[] array) {
        return Arrays.copyOf(array, array.length, Resident[].class);
    }

    @Override
    public AEntity add(final AEntity entity) {
        final AEntity[] entities = arrayUtils.expandArray(residents);
        residents = castArray(entities);
        entity.setId((long) residents.length);
        residents[residents.length - 1] = (Resident) entity;
        return entity;
    }

    public Resident findById(final Long id) {
        for (final Resident resident : residents) {
            if (resident.getId().equals(id)) {
                return resident;
            }
        }
        return null;
    }

    public void addHistory(final Resident resident, final RoomHistory history) {
        resident.setHistory(history);
    }

    public int showTotalNumber() {
        return residents.length;
    }

    public Resident[] getResidents() {
        return residents;
    }
}
