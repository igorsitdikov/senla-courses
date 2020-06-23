package com.senla.hotel.repository.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;

import java.util.List;

public interface IResidentRepository {
    AEntity add(AEntity entity);

    AEntity findById(Long id);

    void addHistory(Resident resident, RoomHistory history);

    int showTotalNumber();

    List<Resident> getResidents();
}
