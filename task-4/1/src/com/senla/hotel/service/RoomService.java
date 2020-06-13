package com.senla.hotel.service;

import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.entity.type.RoomStatus;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.service.interfaces.IRoomService;
import com.senla.hotel.utils.ArrayUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class RoomService implements IRoomService {
    private RoomRepository roomRepository = new RoomRepository();
    private ArrayUtils arrayUtils = new ArrayUtils();

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

    public void updateCheckOutHistory(final Long id, final RoomHistory history, final LocalDate checkOut)
        throws NoSuchEntityException {
        final Room room = findRoomById(id);
        final RoomHistory[] histories = room.getHistories();
        for (int i = 0; i < histories.length; i++) {
            if (histories[i].equals(history)) {
                histories[i].setCheckOut(checkOut);
            }
        }
    }

    public void updatePrice(final Long id, final BigDecimal price) throws NoSuchEntityException {
        final Room room = findRoomById(id);
        room.setPrice(price);
    }

    @Override
    public Room[] sortRooms(final Room[] rooms, final Comparator<Room> comparator) {
        final Room[] result = rooms.clone();
        Arrays.sort(result, comparator);
        return result;
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

    public Room[] vacantOnDate(final LocalDate date) {
        final Room[] rooms = roomRepository.getRooms();
        Room[] result = new Room[0];
        for (final Room room : rooms) {
            final RoomHistory[] histories = room.getHistories();
            if (room.getStatus() != RoomStatus.REPAIR &&
                (histories.length == 0 || histories[histories.length - 1].getCheckOut().isBefore(date))) {
                result = arrayUtils.expandArray(Room.class, result);
                result[result.length - 1] = room;
                if (histories.length > 0) {
                    System.out.println(histories[histories.length - 1]);
                }
            }
        }
        for (final Room room : result) {
            System.out.println(room);
        }
        return result;
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
