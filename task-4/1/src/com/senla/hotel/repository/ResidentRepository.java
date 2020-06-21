package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;

import java.util.ArrayList;
import java.util.List;

public class ResidentRepository extends ARepository {

    private static List<Resident> residents = new ArrayList<>();

    public ResidentRepository() {
    }

    public ResidentRepository(final List<Resident> residents) {
        this.residents = residents;
    }

    @Override
    public AEntity add(final AEntity entity) {
        entity.setId((long) residents.size());
        residents.add((Resident) entity);
        return entity;
    }

    public AEntity findById(final Long id) {
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
        return residents.size();
    }

    public List<Resident> getResidents() {
        return residents;
    }
}
