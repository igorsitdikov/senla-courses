package com.senla.hotel.service;

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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        final List<RoomHistory> histories = room.getHistories();
        for (final RoomHistory roomHistory : histories) {
            if (roomHistory.equals(history)) {
                roomHistory.setCheckOut(checkOut);
            }
        }
    }

    @Override
    public List<Room> showVacantRoomsOnDate(final LocalDate date) {
        return vacantOnDate(date);
    }

    @Override
    public List<Room> sortRooms(final List<Room> rooms, final Comparator<Room> comparator) {
        final List<Room> result = new ArrayList<>(rooms);
        result.sort(comparator);
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
    public List<Room> vacantOnDate(final LocalDate date) {
        final List<Room> rooms = roomRepository.getRooms();
        List<Room> result = new ArrayList<>();
        for (final Room room : rooms) {
            final List<RoomHistory> histories = room.getHistories();
            if (room.getStatus() != RoomStatus.REPAIR &&
                    (histories.size() == 0 || histories.get(histories.size() - 1).getCheckOut().isBefore(date))) {
                result.add(room);
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
    public List<Room> showAllRooms() {
        return roomRepository.getRooms();
    }

    @Override
    public List<Room> showAllRoomsSortedByPrice() {
        final List<Room> rooms = showAllRooms();
        return sortRooms(rooms, new RoomPriceComparator());
    }

    @Override
    public List<Room> showAllRoomsSortedByAccommodation() {
        final List<Room> rooms = showAllRooms();
        return sortRooms(rooms, new RoomAccommodationComparator());
    }

    @Override
    public List<Room> showAllRoomsSortedByStars() {
        final List<Room> rooms = showAllRooms();
        return sortRooms(rooms, new RoomStarsComparator());
    }

    @Override
    public List<Room> showVacantRooms() {
        return roomRepository.getVacantRooms();
    }

    @Override
    public List<Room> showVacantRoomsSortedByPrice() {
        final List<Room> rooms = showVacantRooms();
        return sortRooms(rooms, new RoomPriceComparator());
    }

    @Override
    public List<Room> showVacantRoomsSortedByAccommodation() {
        final List<Room> rooms = showVacantRooms();
        return sortRooms(rooms, new RoomAccommodationComparator());
    }

    @Override
    public List<Room> showVacantRoomsSortedByStars() {
        final List<Room> rooms = showVacantRooms();
        return sortRooms(rooms, new RoomStarsComparator());
    }

    @Override
    public Room showRoomDetails(final Integer number) throws NoSuchEntityException {
        return findRoomByRoomNumber(number);
    }

    @Override
    public List<Resident> showLastResidents(final Room room, final Integer number) throws NoSuchEntityException {
        final Long id = room.getId();
        return showLastResidents(id, number);
    }

    public List<Resident> showLastResidents(final Long id, final Integer number) throws NoSuchEntityException {
        final List<RoomHistory> histories = findRoomById(id).getHistories();
        List<Resident> residents = new ArrayList<>();
        if (histories.size() > number) {
            for (int i = histories.size() - number; i < histories.size(); i++) {
                residents.add(findRoomById(id).getHistories().get(i).getResident());
            }
        } else {
            for (int i = 0; i < histories.size(); i++) {
                residents.add(findRoomById(id).getHistories().get(i).getResident());
            }
        }
        return residents;
    }
}