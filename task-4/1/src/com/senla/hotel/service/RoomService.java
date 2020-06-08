package com.senla.hotel.service;

import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.entity.type.RoomStatus;
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

    @Override
    public void showRooms(final Room[] rooms) {
        for (final Room room : rooms) {
            System.out.println(room.toString());
        }
    }

    @Override
    public void addHistoryToRoom(final Long id, final RoomHistory history) throws NoSuchEntityException {
        final Room room = findRoomById(id);
        roomRepository.addHistory(room, history);
    }

    @Override
    public Room[] sortRooms(final Room[] rooms, final Comparator<Room> comparator) {
        Arrays.sort(rooms, comparator);
        return rooms;
    }

    @Override
    public void showDetails(final Long id) throws NoSuchEntityException {
        final Room room = findRoomById(id);
        System.out.println(room);
    }

    @Override
    public Room findRoomById(final Long id) throws NoSuchEntityException {
        final Room room = roomRepository.findById(id);
        if (room == null) {
            throw new NoSuchEntityException(String.format("No room with id %d%n", id));
        }
        return room;
    }

    @Override
    public Room update(final Long id, final RoomStatus roomStatus) {
        final Room room = roomRepository.findById(id);
        room.setStatus(roomStatus);
        return room;
    }

    @Override
    public int countVacantRooms() {
        return roomRepository.countVacantRooms();
    }

    @Override
    public Room[] getAllRooms() {
        return roomRepository.getRooms();
    }

    @Override
    public Room[] getVacantRooms() {
        return roomRepository.getVacantRooms();
    }
}
