package com.senla.hotel.mapper;

import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.csvMapper.RoomHistoryMapper;
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
    private RoomDao roomDao;
    @Autowired
    private ResidentDao residentDao;
    @Autowired
    private AttendanceDao attendanceDao;

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
            final Room room = roomDao.findById(id);
            history.setRoom(room);
        } catch (final PersistException e) {
            logger.error("No such room with id {} {}", elements[3], e);
            e.printStackTrace();
        }
        try {
            final long id = Long.parseLong(elements[4]);
            final Resident resident = residentDao.findById(id);
            history.setResident(resident);
        } catch (final PersistException e) {
            logger.error("No such resident with id {} {}", elements[4], e);
        }
        history.setStatus(HistoryStatus.valueOf(elements[5]));
        final List<Attendance> historyList = new ArrayList<>();
        if (elements.length > 6) {
            for (int i = 6; i < elements.length; i++) {
                try {
                    historyList.add(attendanceDao.findById(Long.parseLong(elements[i])));
                } catch (final PersistException e) {
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
