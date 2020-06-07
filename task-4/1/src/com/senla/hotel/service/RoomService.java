package com.senla.hotel.service;

import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.service.interfaces.IRoomService;

import java.util.Arrays;
import java.util.Comparator;

public class RoomService implements IRoomService {
    private RoomRepository roomRepository = new RoomRepository();

    @Override
    public void add(final Room room) {
        roomRepository.add(room);
    }

    public void showRooms(final Room[] rooms) {
        for (int i = 0; i < rooms.length; i++) {
            System.out.println(rooms[i].toString());
        }
    }

    @Override
    public Room[] sortRooms(final Room[] rooms, final Comparator<Room> comparator) {
        Arrays.sort(rooms, comparator);
        return rooms;
    }

    @Override
    public int getTotalEmptyRooms() {
        return roomRepository.countEmptyRooms();
    }

    @Override
    public void showEmptyRoomsByDate() {

    }

    @Override
    public void showLatestResidents(final Long id, final Integer number) {

    }

    @Override
    public void showDetails(final Long id) throws NoSuchEntityException {
        final Room room = getRoomById(id);
        if (room == null) {
            throw new NoSuchEntityException(String.format("No room with id %d%n", id));
        }
        System.out.println(room);

    }

    public Room getRoomById(final Long id) {
        for (int i = 0; i < roomRepository.getRooms().length; i++) {
            if (roomRepository.getRooms()[i].getId().equals(id)) {
                return roomRepository.getRooms()[i];
            }
        }
        return null;
    }

    public Room[] getAllRooms() {
        return roomRepository.getRooms();
    }

    public Room[] getVacantRooms() {
        return roomRepository.getVacantRooms();
    }
}
