package com.senla.hotel.service.interfaces;

import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    List<RoomDto> showVacantRoomsOnDate(LocalDate date) throws PersistException;

    RoomDto findById(Long id) throws EntityNotFoundException, PersistException;

    RoomDto findByNumber(Integer number) throws EntityNotFoundException, PersistException;

    List<ResidentDto> showLastResidents(Long id, Integer number) throws EntityNotFoundException, PersistException;

    void importRooms() throws PersistException;

    void exportRooms() throws PersistException;

    List<ResidentDto> showLastResidents(RoomDto room, Integer number) throws EntityNotFoundException, PersistException;

    Long countVacantRooms() throws PersistException;

    void changeRoomPrice(Integer number, BigDecimal price) throws EntityNotFoundException, PersistException;

    void changeRoomStatus(Long id, RoomStatus status) throws EntityNotFoundException, PersistException;

    void changeRoomStatus(Integer number, RoomStatus status) throws EntityNotFoundException, PersistException;

    void addRoom(RoomDto room) throws PersistException;

    List<RoomDto> showAll(SortField sortField) throws PersistException;

    List<RoomDto> showVacant(SortField sortField) throws PersistException;

    RoomDto showRoomDetails(Integer number) throws EntityNotFoundException, PersistException;
}
