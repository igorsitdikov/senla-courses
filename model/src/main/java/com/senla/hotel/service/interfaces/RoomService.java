package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public interface RoomService {

    List<Room> showVacantRoomsOnDate(LocalDate date);

    List<Room> sortRooms(List<Room> rooms, Comparator<Room> comparator);

    Room findById(Long id) throws EntityNotFoundException;

    Room findRoomByRoomNumber(Integer number) throws EntityNotFoundException;

    List<Room> vacantOnDate(LocalDate date);

    List<Resident> showLastResidents(Long id, Integer number) throws EntityNotFoundException;

    void importRooms();

    void exportRooms();

    List<Resident> showLastResidents(Room room, Integer number) throws EntityNotFoundException;

    int countVacantRooms();

    void changeRoomPrice(Integer number, BigDecimal price) throws EntityNotFoundException;

    void changeRoomStatus(Long id, RoomStatus status) throws EntityNotFoundException;

    void changeRoomStatus(Integer number, RoomStatus status) throws EntityNotFoundException;

    void addRoom(Room room);

    List<Room> showAll(SortField sortField);

    List<Room> showVacant(SortField sortField);

    Room showRoomDetails(Integer number) throws EntityNotFoundException;
}
