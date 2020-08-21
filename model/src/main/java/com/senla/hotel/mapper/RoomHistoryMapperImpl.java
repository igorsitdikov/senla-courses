package com.senla.hotel.mapper;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.EntityMapper;
import com.senla.hotel.service.interfaces.AttendanceService;
import com.senla.hotel.service.interfaces.ResidentService;
import com.senla.hotel.service.interfaces.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RoomHistoryMapperImpl implements EntityMapper<RoomHistory> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Autowired
    private RoomService roomService;
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
            history.setRoom(roomService.findById(Long.parseLong(elements[3])));
        } catch (final EntityNotFoundException e) {
            System.err.println(String.format("No such room with id %s %s", elements[3], e));
            throw new NullPointerException();
        }
        try {
            history.setResident(residentService.findById(Long.parseLong(elements[4])));
        } catch (final EntityNotFoundException e) {
            System.err.println(String.format("No such resident with id %s %s", elements[4], e));
            throw new NullPointerException();
        }
        final List<Attendance> historyList = new ArrayList<>();
        if (elements.length > 5) {
            for (int i = 5; i < elements.length; i++) {
                try {
                    historyList.add(attendanceService.findById(Long.parseLong(elements[i])));
                } catch (final EntityNotFoundException | PersistException e) {
                    System.err.println(String.format("No such attendance with id %s %s", elements[i], e));
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
        destination.getAttendances()
                .forEach(attendance -> sb.append(attendance.getId()).append(SEPARATOR));

        return sb.toString();
    }
}
