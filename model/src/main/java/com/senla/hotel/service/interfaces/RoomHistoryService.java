package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.util.List;

public interface RoomHistoryService {
    RoomHistory create(RoomHistory history) throws PersistException;

    RoomHistory findById(Long id) throws EntityNotFoundException, PersistException;

    void importHistories();

    void exportHistories() throws PersistException;

    List<RoomHistory> showHistories() throws PersistException;
}
