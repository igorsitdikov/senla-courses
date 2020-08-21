package com.senla.hotel.repository.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;

import java.util.List;

public interface ResidentRepository {
    AEntity create(AEntity entity);

    void update(Resident resident);

    AEntity getById(Long id) throws EntityNotFoundException;

    int showTotalNumber();

    void setResidents(List<Resident> residents);

    List<Resident> getAll();
}
