package com.senla.hotel.service;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.repository.interfaces.IRoomRepository;
import com.senla.hotel.service.interfaces.IRoomService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.comparator.RoomAccommodationComparator;
import com.senla.hotel.utils.comparator.RoomPriceComparator;
import com.senla.hotel.utils.comparator.RoomStarsComparator;
import com.senla.hotel.utils.csv.reader.CsvReader;
import com.senla.hotel.utils.csv.writer.CsvWriter;
import com.senla.hotel.utils.settings.PropertyLoader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class RoomService implements IRoomService {
    private static final Integer AMOUNT_HISTORIES =
        Integer.parseInt(PropertyLoader.getInstance().getProperty("amount-histories"));

    private static final String PROPERTY = "rooms";
    private static RoomService roomService;
    private final IRoomRepository roomRepository = RoomRepository.getInstance();

    private RoomService() {
    }

    public static RoomService getInstance() {
        if (roomService == null) {
            roomService = new RoomService();
        }
        return roomService;
    }

    @Override
    public void addHistoryToRoom(final Long id, final RoomHistory history) throws EntityNotFoundException {
        final Room room = findById(id);
        final LinkedList<RoomHistory> histories = new LinkedList<>(room.getHistories());
        if (histories.size() == AMOUNT_HISTORIES) {
            histories.removeFirst();
        }
        room.setHistories(histories);
        roomRepository.addHistory(room, history);
    }

    @Override
    public void updateCheckOutHistory(final Long id, final RoomHistory history, final LocalDate checkOut)
        throws EntityNotFoundException {
        final Room room = findById(id);
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
    public Room findById(final Long id) throws EntityNotFoundException {
        final Room room = (Room) roomRepository.findById(id);
        if (room == null) {
            throw new EntityNotFoundException(String.format("No room with id %d%n", id));
        }
        return room;
    }

    @Override
    public Room findRoomByRoomNumber(final Integer number) throws EntityNotFoundException {
        final Room room = (Room) roomRepository.findByRoomNumber(number);
        if (room == null) {
            throw new EntityNotFoundException(String.format("No room with № %d%n", number));
        }
        return room;
    }

    @Override
    public List<Room> vacantOnDate(final LocalDate date) {
        final List<Room> rooms = roomRepository.getRooms();
        final List<Room> result = new ArrayList<>();
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
    public void changeRoomPrice(final Long id, final BigDecimal price) throws EntityNotFoundException {
        changePrice(id, price);
    }

    @Override
    public void changeRoomPrice(final Integer number, final BigDecimal price) throws EntityNotFoundException {
        changePrice(number, price);
    }

    private void changePrice(final Long id, final BigDecimal price) throws EntityNotFoundException {
        roomRepository.changePrice(id, price);
    }

    private void changePrice(final Integer number, final BigDecimal price) throws EntityNotFoundException {
        roomRepository.changePrice(number, price);
    }

    @Override
    public void changeRoomStatus(final Long id, final RoomStatus status) throws EntityNotFoundException {
        final Room room = findById(id);
        if (room.getStatus() == RoomStatus.OCCUPIED && status == RoomStatus.OCCUPIED) {
            System.out.println("Room is already occupied");
        } else if (room.getStatus() == RoomStatus.REPAIR && status == RoomStatus.OCCUPIED) {
            System.out.println("Room is not available now");
        } else {
            room.setStatus(status);
        }
    }

    @Override
    public void changeRoomStatus(final Integer number, final RoomStatus status) throws EntityNotFoundException {
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
    public Room showRoomDetails(final Integer number) throws EntityNotFoundException {
        return findRoomByRoomNumber(number);
    }

    @Override
    public void importRooms() {
        final List<Room> rooms = ParseUtils.stringToRooms(CsvReader.getInstance().read(PROPERTY));
        roomRepository.setRooms(rooms);
    }

    @Override
    public void exportRooms() {
        CsvWriter.getInstance().write(PROPERTY, ParseUtils.roomsToCsv());
    }

    @Override
    public List<Resident> showLastResidents(final Room room, final Integer amount) throws EntityNotFoundException {
        final Long id = room.getId();
        return showLastResidents(id, amount);
    }

    public List<Resident> showLastResidents(final Long id, final Integer amount) throws EntityNotFoundException {
        final List<RoomHistory> histories = findById(id).getHistories();
        final List<Resident> residents = new ArrayList<>();
        if (histories.size() > amount) {
            for (int i = histories.size() - amount; i < histories.size(); i++) {
                residents.add(findById(id).getHistories().get(i).getResident());
            }
        } else {
            for (int i = 0; i < histories.size(); i++) {
                residents.add(findById(id).getHistories().get(i).getResident());
            }
        }
        return residents;
    }
}
