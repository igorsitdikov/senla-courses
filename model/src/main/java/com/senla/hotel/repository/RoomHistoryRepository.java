package com.senla.hotel.repository;

import com.senla.annotaion.Singleton;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.repository.interfaces.IRoomHistoryRepository;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class RoomHistoryRepository implements IRoomHistoryRepository {
    private static List<RoomHistory> histories = new ArrayList<>();
    private static Long counter = 0L;

    @Override
    public AEntity findById(final Long id) throws EntityNotFoundException {
        for (final RoomHistory history : histories) {
            if (history.getId().equals(id)) {
                return history;
            }
        }
        throw new EntityNotFoundException(String.format("No room history with id %d%n", id));
    }

    @Override
    public void addAttendance(final Long id, final Attendance attendance) throws EntityNotFoundException {
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
    public void setCounter(final Long counter) {
        RoomHistoryRepository.counter = counter;
    }

    @Override
    public List<RoomHistory> getHistories() {
        return histories;
    }
}
