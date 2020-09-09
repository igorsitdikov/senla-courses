package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.dto.RoomDTO;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.enumerated.Type;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.csvMapper.RoomMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.service.interfaces.RoomService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.comparator.RoomAccommodationComparator;
import com.senla.hotel.utils.comparator.RoomPriceComparator;
import com.senla.hotel.utils.comparator.RoomStarsComparator;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class RoomServiceImpl implements RoomService {

    private static final Logger logger = LogManager.getLogger(RoomServiceImpl.class);

    @Autowired
    private CsvReader csvReader;
    @Autowired
    private CsvWriter csvWriter;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private ResidentDao residentDao;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private RoomDtoMapper roomDtoMapper;
    @Autowired
    private ResidentDtoMapper residentDtoMapper;
    @PropertyLoad(propertyName = "rooms")
    private String property;
    @PropertyLoad(type = Type.INTEGER)
    private Integer amountHistories;

    @Override
    public List<RoomDTO> showVacantRoomsOnDate(final LocalDate date) throws PersistException, EntityIsEmptyException {
        List<RoomDTO> list = new ArrayList<>();
        RoomDtoMapper roomDtoMapper1 = roomDtoMapper;
        for (Room room : roomDao.getVacantOnDate(date)) {
            RoomDTO roomDTO = roomDtoMapper1.destinationToSource(room);
            list.add(roomDTO);
        }
        return list;
    }

    @Override
    public RoomDTO findById(final Long id) throws EntityNotFoundException, PersistException, EntityIsEmptyException {
        final Room room = roomDao.findById(id);
        if (room == null) {
            throw new EntityNotFoundException(String.format("No room with id %d%n", id));
        }
        return roomDtoMapper.destinationToSource(room);
    }

    @Override
    public RoomDTO findByNumber(final Integer number) throws EntityNotFoundException, PersistException, EntityIsEmptyException {
        final Room room = roomDao.findByNumber(number);
        if (room == null) {
            throw new EntityNotFoundException(String.format("No room with № %d%n", number));
        }
        return roomDtoMapper.destinationToSource(room);
    }

    @Override
    public int countVacantRooms() throws PersistException {
        return roomDao.getVacantRooms().size();
    }

    @Override
    public void changeRoomPrice(final Integer number, final BigDecimal price)
        throws PersistException {
        final Room room = roomDao.findByNumber(number);
        room.setPrice(price);
        roomDao.update(room);
    }

    @Override
    public void changeRoomStatus(final Long id, final RoomStatus status)
        throws EntityNotFoundException, PersistException {
        final Room room = roomDao.findById(id);
        if (room.getStatus() == RoomStatus.OCCUPIED && status == RoomStatus.OCCUPIED) {
            logger.warn("Room is already occupied");
        } else if (room.getStatus() == RoomStatus.REPAIR && status == RoomStatus.OCCUPIED) {
            logger.warn("Room is not available now");
        } else {
            room.setStatus(status);
            roomDao.update(room);
        }
    }

    @Override
    public void changeRoomStatus(final Integer number, final RoomStatus status)
        throws EntityNotFoundException, PersistException {
        final Room room = roomDao.findByNumber(number);
        changeRoomStatus(room.getId(), status);
    }

    @Override
    public void addRoom(final RoomDTO room) throws PersistException, EntityIsEmptyException {
        Room entity = roomDtoMapper.sourceToDestination(room);
        roomDao.create(entity);
    }

    @Override
    public List<RoomDTO> showAll(final SortField sortField) throws PersistException, EntityIsEmptyException {
        final List<Room> rooms = roomDao.getAll();
        final List<RoomDTO> roomsDto = new ArrayList<>();
        for (Room room : rooms) {
            RoomDTO roomDTO = roomDtoMapper.destinationToSource(room);
            roomsDto.add(roomDTO);
        }
        return sortRooms(sortField, roomsDto);
    }

    private List<RoomDTO> sortRooms(final SortField sortField, final List<RoomDTO> rooms) {
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

    private List<RoomDTO> sortRooms(final List<RoomDTO> rooms, final Comparator<RoomDTO> comparator) {
        final List<RoomDTO> result = new ArrayList<>(rooms);
        result.sort(comparator);
        return result;
    }

    @Override
    public List<RoomDTO> showVacant(final SortField sortField) throws PersistException, EntityIsEmptyException {
        final List<Room> rooms = roomDao.getVacantRooms();
        final List<RoomDTO> roomsDTO = new ArrayList<>();
        for (Room room : rooms) {
            RoomDTO roomDTO = roomDtoMapper.destinationToSource(room);
            roomsDTO.add(roomDTO);
        }
        return sortRooms(sortField, roomsDTO);
    }

    @Override
    public RoomDTO showRoomDetails(final Integer number) throws EntityNotFoundException, PersistException, EntityIsEmptyException {
        Room room = roomDao.findByNumber(number);
        return roomDtoMapper.destinationToSource(room);
    }

    @Override
    public void importRooms() throws PersistException {
        final List<Room> rooms = ParseUtils.stringToEntities(csvReader.read(property), roomMapper, Room.class);
        roomDao.insertMany(rooms);
    }

    @Override
    public void exportRooms() throws PersistException {
        csvWriter.write(property, ParseUtils.entitiesToCsv(roomMapper, roomDao.getAll()));
    }

    @Override
    public List<ResidentDTO> showLastResidents(final RoomDTO room, final Integer amount) throws PersistException, EntityIsEmptyException {
        final Long id = room.getId();
        return showLastResidents(id, amount);
    }

    public List<ResidentDTO> showLastResidents(final Long id, final Integer amount) throws PersistException, EntityIsEmptyException {
        List<Resident> entities = residentDao.getLastResidentsByRoomId(id, amount);
        List<ResidentDTO> residentDTOS = new ArrayList<>();
        for (Resident entity : entities) {
            ResidentDTO residentDTO = residentDtoMapper.destinationToSource(entity);
            residentDTOS.add(residentDTO);
        }
        return residentDTOS;
    }
}
