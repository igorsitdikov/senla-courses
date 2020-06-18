package com.senla.hotel.service;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.repository.ResidentRepository;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.service.interfaces.IRoomService;
import com.senla.hotel.utils.ArrayUtils;
import com.senla.hotel.utils.comparator.RoomAccommodationComparator;
import com.senla.hotel.utils.comparator.RoomPriceComparator;
import com.senla.hotel.utils.comparator.RoomStarsComparator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class RoomService implements IRoomService {
    private RoomRepository roomRepository = new RoomRepository();
    private ResidentRepository residentRepository = new ResidentRepository();
    private ArrayUtils arrayUtils = new ArrayUtils();

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
    public Room[] showVacantRoomsOnDate(final LocalDate date) {
        return vacantOnDate(date);
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
                final AEntity[] entities = arrayUtils.expandArray(result);
                result = roomRepository.castArray(entities);
                result[result.length - 1] = room;
            }
        }
        return result;
    }

    @Override
    public int countVacantRooms() {
        return roomRepository.countVacantRooms();
    }

    @Override
    public void changeRoomPrice(final Long id, final BigDecimal price) {
        changePrice(id, price);
    }

    @Override
    public void changeRoomPrice(final Integer number, final BigDecimal price) {
        changePrice(number, price);
    }

    private void changePrice(final Long id, final BigDecimal price) {
        roomRepository.changePrice(id, price);
    }

    private void changePrice(final Integer number, final BigDecimal price) {
        roomRepository.changePrice(number, price);
    }

    @Override
    public void changeRoomStatus(final Long id, final RoomStatus status) throws NoSuchEntityException {
        final Room room = findRoomById(id);
        if (room.getStatus() == RoomStatus.OCCUPIED && status == RoomStatus.OCCUPIED) {
            System.out.println("Room is already occupied");
        } else if (room.getStatus() == RoomStatus.REPAIR && status == RoomStatus.OCCUPIED) {
            System.out.println("Room is not available now");
        } else {
            room.setStatus(status);
        }
    }

    @Override
    public void changeRoomStatus(final Integer number, final RoomStatus status) throws NoSuchEntityException {
        final Room room = findRoomByRoomNumber(number);
        changeRoomStatus(room.getId(), status);
    }

    @Override
    public void addRoom(final Room room) {
        roomRepository.add(room);
    }

    @Override
    public Room[] showAllRooms() {
        return roomRepository.getRooms();
    }

    @Override
    public Room[] showAllRoomsSortedByPrice() {
        final Room[] rooms = showAllRooms();
        return sortRooms(rooms, new RoomPriceComparator());
    }

    @Override
    public Room[] showAllRoomsSortedByAccommodation() {
        final Room[] rooms = showAllRooms();
        return sortRooms(rooms, new RoomAccommodationComparator());
    }

    @Override
    public Room[] showAllRoomsSortedByStars() {
        final Room[] rooms = showAllRooms();
        return sortRooms(rooms, new RoomStarsComparator());
    }

    @Override
    public Room[] showVacantRooms() {
        return roomRepository.getVacantRooms();
    }

    @Override
    public Room[] showVacantRoomsSortedByPrice() {
        final Room[] rooms = showVacantRooms();
        return sortRooms(rooms, new RoomPriceComparator());
    }

    @Override
    public Room[] showVacantRoomsSortedByAccommodation() {
        final Room[] rooms = showVacantRooms();
        return sortRooms(rooms, new RoomAccommodationComparator());
    }

    @Override
    public Room[] showVacantRoomsSortedByStars() {
        final Room[] rooms = showVacantRooms();
        return sortRooms(rooms, new RoomStarsComparator());
    }

    @Override
    public Room showRoomDetails(final Integer number) throws NoSuchEntityException {
        return findRoomByRoomNumber(number);
    }

    @Override
    public Resident[] showLastResidents(final Room room, final Integer number) throws NoSuchEntityException {
        final Long id = room.getId();
        return showLastResidents(id, number);
    }

    public Resident[] showLastResidents(final Long id, final Integer number) throws NoSuchEntityException {
        final RoomHistory[] histories = findRoomById(id).getHistories();
        Resident[] residents = new Resident[0];
        if (histories.length > number) {
            for (int i = histories.length - number; i < histories.length; i++) {
                final AEntity[] entities = arrayUtils.expandArray(residents);
                residents = residentRepository.castArray(entities);
                residents[residents.length - 1] = findRoomById(id).getHistories()[i].getResident();
            }
        } else {
            for (int i = 0; i < histories.length; i++) {
                final AEntity[] entities = arrayUtils.expandArray(residents);
                residents = residentRepository.castArray(entities);
                residents[residents.length - 1] = findRoomById(id).getHistories()[i].getResident();
            }
        }
        return residents;
    }
}
