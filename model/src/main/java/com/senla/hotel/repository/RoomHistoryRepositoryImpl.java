package com.senla.hotel.repository;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.repository.interfaces.RoomHistoryRepository;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class RoomHistoryRepositoryImpl implements RoomHistoryRepository {
    private static List<RoomHistory> histories = new ArrayList<>();
    private static Long counter = 0L;

    @Autowired
    private RoomHistoryDao roomHistoryDao;
    @Override
    public AEntity findById(final Long id) throws EntityNotFoundException {
        try {
            return roomHistoryDao.getById(id);
        } catch (PersistException e) {
            e.printStackTrace();
        }

//        for (final RoomHistory history : histories) {
//            if (history.getId().equals(id)) {
//                return history;
//            }
//        }
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
        try {
            return roomHistoryDao.create((RoomHistory) entity);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
//        entity.setId(++counter);
//        histories.add((RoomHistory) entity);
//        return entity;
    }

    @Override
    public void setHistories(final List<RoomHistory> roomHistories) {
        RoomHistoryRepositoryImpl.histories = new ArrayList<>(roomHistories);
    }

    @Override
    public void setCounter(final Long counter) {
        RoomHistoryRepositoryImpl.counter = counter;
    }

    @Override
    public List<RoomHistory> getHistories() {
        return histories;
    }
}
