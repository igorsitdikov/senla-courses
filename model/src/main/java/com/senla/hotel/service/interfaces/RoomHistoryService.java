package com.senla.hotel.service.interfaces;

import com.senla.hotel.dto.RoomHistoryDto;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.util.List;

public interface RoomHistoryService {

    RoomHistoryDto create(RoomHistoryDto history) throws PersistException;

    RoomHistoryDto findById(Long id) throws EntityNotFoundException, PersistException;

    void importHistories() throws PersistException;

    void exportHistories() throws PersistException;

    List<RoomHistoryDto> showHistories() throws PersistException;
}
