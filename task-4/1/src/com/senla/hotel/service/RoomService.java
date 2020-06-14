package com.senla.hotel.service;

import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;
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

    @Override
    public void updateCheckOutHistory(final Long id, final RoomHistory history, final LocalDate checkOut)
        throws NoSuchEntityException {
        final Room room = findRoomById(id);
        final RoomHistory[] histories = room.getHistories();
        for (final RoomHistory roomHistory : histories) {
            if (roomHistory.equals(history)) {
                roomHistory.setCheckOut(checkOut);
            }
        }
    }

    @Override
    public void updatePrice(final Long id, final BigDecimal price) {
        roomRepository.updatePrice(id, price);
    }

    @Override
    public void updatePrice(final Integer number, final BigDecimal price) {
        roomRepository.updatePrice(number, price);
    }

    @Override
    public Room[] sortRooms(final Room[] rooms, final Comparator<Room> comparator) {
        final Room[] result = rooms.clone();
        Arrays.sort(result, comparator);
        return result;
    }

    @Override
    public Room findRoomById(final Long id) throws NoSuchEntityException {
        final Room room = (Room) roomRepository.findById(id);
        if (room == null) {
            throw new NoSuchEntityException(String.format("No room with id %d%n", id));
        }
        return room;
    }

    @Override
    public Room findRoomByRoomNumber(final Integer number) throws NoSuchEntityException {
        final Room room = (Room) roomRepository.findByRoomNumber(number);
        if (room == null) {
            throw new NoSuchEntityException(String.format("No room with № %d%n", number));
        }
        return room;
    }

    @Override
    public Room[] vacantOnDate(final LocalDate date) {
        final Room[] rooms = roomRepository.getRooms();
        Room[] result = new Room[0];
        for (final Room room : rooms) {
            final RoomHistory[] histories = room.getHistories();
            if (room.getStatus() != RoomStatus.REPAIR &&
                (histories.length == 0 || histories[histories.length - 1].getCheckOut().isBefore(date))) {
                result = arrayUtils.expandArray(Room.class, result);
                result[result.length - 1] = room;
            }
        }
        return result;
    }

    @Override
    public Room update(final Long id, final RoomStatus roomStatus) {
        final Room room = (Room) roomRepository.findById(id);
        room.setStatus(roomStatus);
        return room;
    }

    @Override
    public Room update(final Integer number, final RoomStatus roomStatus) {
        final Room room = (Room) roomRepository.findByRoomNumber(number);
        return update(room.getId(), roomStatus);
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
