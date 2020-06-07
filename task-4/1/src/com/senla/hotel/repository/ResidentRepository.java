package com.senla.hotel.repository;

import com.senla.hotel.entity.Resident;

public class ResidentRepository extends AbstractRepository<Resident> {

    private Resident[] residents = new Resident[0];

    public ResidentRepository() {
    }

    public ResidentRepository(final Resident[] residents) {
        this.residents = residents;
    }

    @Override
    public Resident add(final Resident entity) {
        residents = arrayUtils.expandArray(Resident.class, residents);
        entity.setId((long) residents.length);
        residents[residents.length - 1] = entity;
        return entity;
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
