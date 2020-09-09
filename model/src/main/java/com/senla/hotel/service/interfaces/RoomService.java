package com.senla.hotel.service.interfaces;

import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.dto.RoomDTO;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    List<RoomDTO> showVacantRoomsOnDate(LocalDate date) throws PersistException, EntityIsEmptyException;

    RoomDTO findById(Long id) throws EntityNotFoundException, PersistException, EntityIsEmptyException;

    RoomDTO findByNumber(Integer number) throws EntityNotFoundException, PersistException, EntityIsEmptyException;

    List<ResidentDTO> showLastResidents(Long id, Integer number) throws EntityNotFoundException, PersistException, EntityIsEmptyException;

    void importRooms() throws PersistException;

    void exportRooms() throws PersistException;

    List<ResidentDTO> showLastResidents(RoomDTO room, Integer number) throws EntityNotFoundException, PersistException, EntityIsEmptyException;

    int countVacantRooms() throws PersistException;

    void changeRoomPrice(Integer number, BigDecimal price) throws EntityNotFoundException, PersistException;

    void changeRoomStatus(Long id, RoomStatus status) throws EntityNotFoundException, PersistException;

    void changeRoomStatus(Integer number, RoomStatus status) throws EntityNotFoundException, PersistException;

    void addRoom(RoomDTO room) throws PersistException, EntityIsEmptyException;

    List<RoomDTO> showAll(SortField sortField) throws PersistException, EntityIsEmptyException;

    List<RoomDTO> showVacant(SortField sortField) throws PersistException, EntityIsEmptyException;

    RoomDTO showRoomDetails(Integer number) throws EntityNotFoundException, PersistException, EntityIsEmptyException;
}
