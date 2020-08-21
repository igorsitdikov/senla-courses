package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.enumerated.Type;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.mapper.RoomMapperImpl;
import com.senla.hotel.mapper.interfaces.EntityMapper;
import com.senla.hotel.repository.interfaces.RoomRepository;
import com.senla.hotel.service.interfaces.RoomService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.comparator.RoomAccommodationComparator;
import com.senla.hotel.utils.comparator.RoomPriceComparator;
import com.senla.hotel.utils.comparator.RoomStarsComparator;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Singleton
public class RoomServiceImpl implements RoomService {
    @Autowired
    private CsvReader csvReader;
    @Autowired
    private CsvWriter csvWriter;
    @Autowired
    private RoomRepository roomRepository;
    @PropertyLoad(propertyName = "rooms")
    private String property;
    @PropertyLoad(type = Type.INTEGER)
    private Integer amountHistories;

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
            roomRepository.update(room);
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
    public List<Room> showAll(final SortField sortField) {
        final List<Room> rooms = roomRepository.getRooms();
        return sortRooms(sortField, rooms);
    }

    private List<Room> sortRooms(final SortField sortField, final List<Room> rooms) {
        switch (sortField) {
            case STARS:
                return sortRooms(rooms, new RoomStarsComparator());
            case ACCOMMODATION:
                return sortRooms(rooms, new RoomAccommodationComparator());
            case PRICE:
                return sortRooms(rooms, new RoomPriceComparator());
            default:
                return rooms;
        }
    }

    @Override
    public List<Room> showVacant(final SortField sortField) {
        final List<Room> rooms = roomRepository.getVacantRooms();
        return sortRooms(sortField, rooms);
    }

    @Override
    public Room showRoomDetails(final Integer number) throws EntityNotFoundException {
        return findRoomByRoomNumber(number);
    }

    @Override
    public void importRooms() {
        final EntityMapper<Room> roomMapper = new RoomMapperImpl();
        final List<Room> rooms = ParseUtils.stringToEntities(csvReader.read(property), roomMapper, Room.class);
        roomRepository.setRooms(rooms);
    }

    @Override
    public void exportRooms() {
        final EntityMapper<Room> roomMapper = new RoomMapperImpl();
        csvWriter.write(property, ParseUtils.entitiesToCsv(roomMapper, roomRepository.getRooms()));
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
