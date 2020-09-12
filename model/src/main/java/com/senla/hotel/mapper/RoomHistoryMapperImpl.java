package com.senla.hotel.mapper;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.csvMapper.RoomHistoryMapper;
import com.senla.hotel.service.RoomServiceImpl;
import com.senla.hotel.service.interfaces.AttendanceService;
import com.senla.hotel.service.interfaces.ResidentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoomHistoryMapperImpl implements RoomHistoryMapper {

    private static final Logger logger = LogManager.getLogger(RoomHistoryMapperImpl.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Autowired
    private RoomServiceImpl roomService;
    @Autowired
    private ResidentService residentService;
    @Autowired
    private AttendanceService attendanceService;

    @Override
    public RoomHistory sourceToDestination(final String source) throws EntityIsEmptyException {
        if (source.isEmpty()) {
            throw new EntityIsEmptyException("RoomHistory is null");
        }

        final String[] elements = source.split(SEPARATOR);
        final RoomHistory history = new RoomHistory();
        history.setId(Long.valueOf(elements[0]));
        history.setCheckIn(LocalDate.parse(elements[1], formatter));
        history.setCheckOut(LocalDate.parse(elements[2], formatter));
        try {
            final long id = Long.parseLong(elements[3]);
            final Room room = roomService.findById(id);
            history.setRoom(room);
        } catch (final EntityNotFoundException e) {
            logger.error("No such room with id {} {}", elements[3], e);
        } catch (final PersistException e) {
            e.printStackTrace();
        }
        try {
            final long id = Long.parseLong(elements[4]);
            final Resident resident = residentService.findById(id);
            history.setResident(resident);
        } catch (final EntityNotFoundException | PersistException e) {
            logger.error("No such resident with id {} {}", elements[4], e);
        }
        history.setStatus(HistoryStatus.valueOf(elements[5]));
        final List<Attendance> historyList = new ArrayList<>();
        if (elements.length > 6) {
            for (int i = 6; i < elements.length; i++) {
                try {
                    historyList.add(attendanceService.findById(Long.parseLong(elements[i])));
                } catch (final EntityNotFoundException | PersistException e) {
                    logger.error("No such attendance with id {} {}", elements[i], e);
                }
            }
        }
        history.setAttendances(historyList);

        return history;
    }

    @Override
    public String destinationToSource(final RoomHistory destination) throws EntityIsEmptyException {
        if (destination == null) {
            throw new EntityIsEmptyException("RoomHistory is empty");
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(destination.getId());
        sb.append(SEPARATOR);
        sb.append(destination.getCheckIn().format(formatter));
        sb.append(SEPARATOR);
        sb.append(destination.getCheckOut().format(formatter));
        sb.append(SEPARATOR);
        sb.append(destination.getRoom().getId());
        sb.append(SEPARATOR);
        sb.append(destination.getResident().getId());
        sb.append(SEPARATOR);
        sb.append(destination.getStatus());
        sb.append(SEPARATOR);
        destination.getAttendances()
            .forEach(attendance -> sb.append(attendance.getId()).append(SEPARATOR));

        return sb.toString();
    }
}
