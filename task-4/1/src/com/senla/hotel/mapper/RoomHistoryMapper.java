package com.senla.hotel.mapper;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.mapper.interfaces.IEntityMapper;
import com.senla.hotel.service.AttendanceService;
import com.senla.hotel.service.ResidentService;
import com.senla.hotel.service.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RoomHistoryMapper implements IEntityMapper<RoomHistory> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public RoomHistory sourceToDestination(String source) {
        if (source == null) {
            return null;
        }

        final String[] elements = source.split(SEPARATOR);
        RoomHistory history = new RoomHistory();
        history.setId(Long.valueOf(elements[0]));
        history.setCheckIn(LocalDate.parse(elements[1], formatter));
        history.setCheckOut(LocalDate.parse(elements[2], formatter));
        try {
            history.setRoom(RoomService.getInstance().findById(Long.parseLong(elements[3])));
        } catch (NoSuchEntityException e) {
            System.err.println(String.format("No such room with id %s %s", elements[3], e));
            return null;
        }
        try {
            history.setResident(ResidentService.getInstance().findById(Long.parseLong(elements[4])));
        } catch (NoSuchEntityException e) {
            System.err.println(String.format("No such resident with id %s %s", elements[4], e));
            return null;
        }
        List<Attendance> historyList = new ArrayList<>();
        if (elements.length > 5) {
            for (int i = 5; i < elements.length; i++) {
                try {
                    historyList.add(AttendanceService.getInstance().findById(Long.parseLong(elements[i])));
                } catch (NoSuchEntityException e) {
                    System.err.println(String.format("No such attendance with id %s %s", elements[i], e));
                }
            }
        }
        history.setAttendances(historyList);

        return history;
    }

    @Override
    public String destinationToSource(RoomHistory destination) {
        if (destination == null) {
            return null;
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
