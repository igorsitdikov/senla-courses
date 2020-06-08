package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.entity.type.RoomStatus;
import com.senla.hotel.exceptions.NoSuchEntityException;

import java.util.Comparator;

public interface IRoomService {

    void add(Room room);

    void showRooms(Room[] rooms);

    void addHistoryToRoom(Long id, RoomHistory history) throws NoSuchEntityException;

    Room[] sortRooms(Room[] rooms, Comparator<Room> comparator);

    void showDetails(Long id) throws NoSuchEntityException;

    Room findRoomById(Long id) throws NoSuchEntityException;

    Room update(Long id, RoomStatus roomStatus);

    int countVacantRooms();

    Room[] getAllRooms();

    Room[] getVacantRooms();
}
