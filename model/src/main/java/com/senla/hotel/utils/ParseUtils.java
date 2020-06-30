package com.senla.hotel.utils;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.mapper.AttendanceMapper;
import com.senla.hotel.mapper.ResidentMapper;
import com.senla.hotel.mapper.RoomHistoryMapper;
import com.senla.hotel.mapper.RoomMapper;
import com.senla.hotel.mapper.interfaces.IEntityMapper;
import com.senla.hotel.service.AttendanceService;
import com.senla.hotel.service.ResidentService;
import com.senla.hotel.service.RoomHistoryService;
import com.senla.hotel.service.RoomService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParseUtils {
    private static IEntityMapper<Attendance> attendanceMapper = new AttendanceMapper();
    private static IEntityMapper<Room> roomMapper = new RoomMapper();
    private static IEntityMapper<Resident> residentMapper = new ResidentMapper();
    private static IEntityMapper<RoomHistory> historyMapper = new RoomHistoryMapper();

    public static List<Attendance> stringToAttendances(final Stream<String> stream) {
        return stream
            .map(attendance -> {
                try {
                    return attendanceMapper.sourceToDestination(attendance);
                } catch (EntityIsEmptyException e) {
                    System.err.println(e.getMessage());
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public static List<Room> stringToRooms(final Stream<String> stream) {
        return stream
            .map(room -> {
                try {
                    return roomMapper.sourceToDestination(room);
                } catch (EntityIsEmptyException e) {
                    System.err.println(e.getMessage());
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public static List<RoomHistory> stringToHistories(final Stream<String> stream) {
        return stream
            .map(history -> {
                try {
                    return historyMapper.sourceToDestination(history);
                } catch (EntityIsEmptyException e) {
                    System.err.println(e.getMessage());
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public static List<Resident> stringToResidents(final Stream<String> stream) {
        return stream
            .map(resident -> {
                try {
                    return residentMapper.sourceToDestination(resident);
                } catch (EntityIsEmptyException e) {
                    System.err.println(e.getMessage());
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public static List<String> attendancesToCsv() {
        final List<Attendance> attendances = AttendanceService.getInstance().showAttendances();
        return attendances.stream()
            .map(attendance -> {
                try {
                    return attendanceMapper.destinationToSource(attendance);
                } catch (EntityIsEmptyException e) {
                    System.err.println(e.getMessage());
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public static List<String> roomsToCsv() {
        final List<Room> rooms = RoomService.getInstance().showAllRooms();
        return rooms.stream()
            .map(room -> {
                try {
                    return roomMapper.destinationToSource(room);
                } catch (EntityIsEmptyException e) {
                    System.err.println(e.getMessage());
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public static List<String> residentsToCsv() {
        final List<Resident> residents = ResidentService.getInstance().showResidents();
        return residents.stream()
            .map(resident -> {
                try {
                    return residentMapper.destinationToSource(resident);
                } catch (EntityIsEmptyException e) {
                    System.err.println(e.getMessage());
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public static List<String> historiesToCsv() {
        final List<RoomHistory> histories = RoomHistoryService.getInstance().showHistories();
        return histories.stream()
            .map(history -> {
                try {
                    return historyMapper.destinationToSource(history);
                } catch (EntityIsEmptyException e) {
                    System.err.println(e.getMessage());
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

}
