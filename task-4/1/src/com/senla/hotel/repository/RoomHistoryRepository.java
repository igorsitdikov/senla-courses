package com.senla.hotel.repository;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.repository.interfaces.IRoomHistoryRepository;
import com.senla.hotel.service.RoomHistoryService;

import java.util.ArrayList;
import java.util.List;

public class RoomHistoryRepository implements IRoomHistoryRepository {
    private static RoomHistoryRepository roomHistoryRepository;
    private static List<RoomHistory> histories = new ArrayList<>();
    private static Long counter = 0L;

    private RoomHistoryRepository() {
    }

    public static RoomHistoryRepository getInstance() {
        if (roomHistoryRepository == null) {
            roomHistoryRepository = new RoomHistoryRepository();
        }
        return roomHistoryRepository;
    }

    @Override
    public AEntity findById(final Long id) {
        for (final RoomHistory history : histories) {
            if (history.getId().equals(id)) {
                return history;
            }
        }
        return null;
    }

    @Override
    public void addAttendance(final Long id, final Attendance attendance) {
        final RoomHistory history = (RoomHistory) findById(id);
        final List<Attendance> attendanceList = new ArrayList<>(history.getAttendances());
        attendanceList.add(attendance);
        history.setAttendances(attendanceList);
    }

    @Override
    public AEntity add(final AEntity entity) {
        entity.setId(++counter);
        histories.add((RoomHistory) entity);
        return entity;
    }

    @Override
    public void setHistories(final List<RoomHistory> roomHistories) {
        RoomHistoryRepository.histories = new ArrayList<>(roomHistories);
    }

    @Override
    public List<RoomHistory> getHistories() {
        return histories;
    }
}
