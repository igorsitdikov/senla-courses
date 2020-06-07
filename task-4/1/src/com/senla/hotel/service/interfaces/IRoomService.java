package com.senla.hotel.service.interfaces;

import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.NoSuchEntityException;

import java.util.Comparator;

public interface IRoomService {
    void add(final Room room);

    void showRooms(final Room[] rooms);

    Room[] sortRooms(final Room[] rooms, final Comparator<Room> comparator);

    int getTotalEmptyRooms();

    void showEmptyRoomsByDate();

    void showLatestResidents(Long id, Integer number);

    void showDetails(Long id) throws NoSuchEntityException;

    Room[] getAllRooms();

    Room[] getVacantRooms();
}
