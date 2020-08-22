package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.enumerated.Type;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.RoomMapperImpl;
import com.senla.hotel.mapper.interfaces.csvMapper.EntityMapper;
import com.senla.hotel.mapper.interfaces.csvMapper.RoomMapper;
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
    private RoomDao roomRepository;
    @Autowired
    private ResidentDao residentRepository;
    @Autowired
    private RoomMapper roomMapper;
    @PropertyLoad(propertyName = "rooms")
    private String property;
    @PropertyLoad(type = Type.INTEGER)
    private Integer amountHistories;

    @Override
    public List<Room> showVacantRoomsOnDate(final LocalDate date) throws PersistException {
        return roomRepository.getVacantOnDate(date);
    }

    @Override
    public Room findById(final Long id) throws EntityNotFoundException, PersistException {
        final Room room = roomRepository.findById(id);
        if (room == null) {
            throw new EntityNotFoundException(String.format("No room with id %d%n", id));
        }
        return room;
    }

    @Override
    public Room findByNumber(final Integer number) throws EntityNotFoundException, PersistException {
        final Room room = roomRepository.findByNumber(number);
        if (room == null) {
            throw new EntityNotFoundException(String.format("No room with № %d%n", number));
        }
        return room;
    }

    @Override
    public int countVacantRooms() throws PersistException {
        return roomRepository.getVacantRooms().size();
    }

    @Override
    public void changeRoomPrice(final Integer number, final BigDecimal price) throws EntityNotFoundException, PersistException {
        final Room room = findByNumber(number);
        room.setPrice(price);
        roomRepository.update(room);
    }

    @Override
    public void changeRoomStatus(final Long id, final RoomStatus status) throws EntityNotFoundException, PersistException {
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
    public void changeRoomStatus(final Integer number, final RoomStatus status) throws EntityNotFoundException, PersistException {
        final Room room = findByNumber(number);
        changeRoomStatus(room.getId(), status);
    }

    @Override
    public void addRoom(final Room room) throws PersistException {
        roomRepository.create(room);
    }

    @Override
    public List<Room> showAll(final SortField sortField) throws PersistException {
        final List<Room> rooms = roomRepository.getAll();
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

    private List<Room> sortRooms(final List<Room> rooms, final Comparator<Room> comparator) {
        final List<Room> result = new ArrayList<>(rooms);
        result.sort(comparator);
        return result;
    }

    @Override
    public List<Room> showVacant(final SortField sortField) throws PersistException {
        final List<Room> rooms = roomRepository.getVacantRooms();
        return sortRooms(sortField, rooms);
    }

    @Override
    public Room showRoomDetails(final Integer number) throws EntityNotFoundException, PersistException {
        return findByNumber(number);
    }

    @Override
    public void importRooms() throws PersistException {
        final List<Room> rooms = ParseUtils.stringToEntities(csvReader.read(property), roomMapper, Room.class);
        roomRepository.insertMany(rooms);
    }

    @Override
    public void exportRooms() throws PersistException {
        csvWriter.write(property, ParseUtils.entitiesToCsv(roomMapper, roomRepository.getAll()));
    }

    @Override
    public List<Resident> showLastResidents(final Room room, final Integer amount) throws EntityNotFoundException, PersistException {
        final Long id = room.getId();
        return showLastResidents(id, amount);
    }

    public List<Resident> showLastResidents(final Long id, final Integer amount) throws EntityNotFoundException, PersistException {
        return residentRepository.getLastResidentsByRoomId(id, amount);
    }
}
