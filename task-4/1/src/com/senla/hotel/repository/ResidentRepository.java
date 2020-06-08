package com.senla.hotel.repository;

import com.senla.hotel.entity.AbstractEntity;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;

public class ResidentRepository extends AbstractRepository {

    private static Resident[] residents = new Resident[0];

    public ResidentRepository() {
    }

    public ResidentRepository(final Resident[] residents) {
        this.residents = residents;
    }

    @Override
    public AbstractEntity add(final AbstractEntity entity) {
        residents = arrayUtils.expandArray(Resident.class, residents);
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

    public void setResidents(final Resident[] residents) {
        this.residents = residents;
    }
}
