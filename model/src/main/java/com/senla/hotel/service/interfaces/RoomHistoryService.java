package com.senla.hotel.service.interfaces;

import com.senla.hotel.dto.RoomHistoryDTO;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.util.List;

public interface RoomHistoryService {

    RoomHistoryDTO create(RoomHistoryDTO history) throws PersistException, EntityIsEmptyException;

    RoomHistoryDTO findById(Long id) throws EntityNotFoundException, PersistException, EntityIsEmptyException;

    void importHistories() throws PersistException;

    void exportHistories() throws PersistException;

    List<RoomHistoryDTO> showHistories() throws PersistException, EntityIsEmptyException;
}
