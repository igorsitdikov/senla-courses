package com.senla.hotel.repository.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;

import java.util.List;

public interface IResidentRepository {
    AEntity add(AEntity entity);

    AEntity findById(Long id) throws EntityNotFoundException;

    void addHistory(Resident resident, RoomHistory history);

    int showTotalNumber();

    void setResidents(List<Resident> residents);

    List<Resident> getResidents();
}
