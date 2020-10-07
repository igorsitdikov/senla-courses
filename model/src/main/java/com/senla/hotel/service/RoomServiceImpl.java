package com.senla.hotel.service;

import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.csvMapper.RoomMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.service.interfaces.RoomService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
    @Value("${rooms:rooms.csv}")
    private String property;
    @Value("${RoomService.amountHistories:3}")
    private Integer amountHistories;


    @Override
    public List<RoomDto> showVacantRoomsOnDate(final LocalDate date) throws PersistException {
        return roomDao.getVacantOnDate(date)
            .stream()
            .map(roomDtoMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    @Override
    public RoomDto findById(final Long id) throws EntityNotFoundException, PersistException {
        final Room room = roomDao.findById(id);
        if (room == null) {
            throw new EntityNotFoundException(String.format("No room with id %d%n", id));
        }
        return roomDtoMapper.destinationToSource(room);
    }

    @Override
    public RoomDto findByNumber(final Integer number) throws EntityNotFoundException, PersistException {
        final Room room = roomDao.findByNumber(number);
        if (room == null) {
            throw new EntityNotFoundException(String.format("No room with № %d%n", number));
        }
        return roomDtoMapper.destinationToSource(room);
    }

    @Override
    public Long countVacantRooms() throws PersistException {
        return roomDao.countVacantRooms();
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
        throws PersistException {
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
        throws PersistException {
        final Room room = roomDao.findByNumber(number);
        changeRoomStatus(room.getId(), status);
    }

    @Override
    public void addRoom(final RoomDto roomDto) throws PersistException {
        final Room room = roomDtoMapper.sourceToDestination(roomDto);
        roomDao.create(room);
    }

    @Override
    public List<RoomDto> showAll(final SortField sortField) throws PersistException {
        return roomDao.getAllSortedBy(sortField)
            .stream()
            .map(roomDtoMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    @Override
    public List<RoomDto> showVacant(final SortField sortField) throws PersistException {
        return roomDao.getVacantRooms(sortField)
            .stream()
            .map(roomDtoMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    @Override
    public RoomDto showRoomDetails(final Integer number) throws EntityNotFoundException, PersistException {
        return findByNumber(number);
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
    public List<ResidentDto> showLastResidents(final RoomDto room, final Integer amount) throws PersistException {
        final Long id = room.getId();
        return showLastResidents(id, amount);
    }

    public List<ResidentDto> showLastResidents(final Long id, final Integer amount) throws PersistException {
        return residentDao.getLastResidentsByRoomId(id, amount)
            .stream()
            .map(residentDtoMapper::destinationToSource)
            .collect(Collectors.toList());
    }
}
