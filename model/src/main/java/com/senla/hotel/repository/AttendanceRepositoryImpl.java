package com.senla.hotel.repository;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.repository.interfaces.AttendanceRepository;

import java.util.LinkedList;
import java.util.List;

@Singleton
public class AttendanceRepositoryImpl implements AttendanceRepository {
    @Autowired
    private AttendanceDao attendanceDao;
    private static List<Attendance> attendances = new LinkedList<>();

    @Override
    public AEntity create(final AEntity entity) throws EntityAlreadyExistsException {
        try {
           return attendanceDao.create((Attendance) entity);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        throw new EntityAlreadyExistsException("Attendance already exists");
    }

    @Override
    public void update(final Attendance attendance) throws EntityNotFoundException {
        try {
            attendanceDao.update(attendance);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AEntity findById(final Long id) throws EntityNotFoundException {
        try {
            return attendanceDao.findById(id);
        } catch (PersistException e) {
            throw new EntityNotFoundException(String.format("No attendance with id %d%n", id));
        }
    }

    @Override
    public void setAttendances(final List<Attendance> attendances) {
//        AttendanceRepositoryImpl.attendances.clear();
//        AttendanceRepositoryImpl.attendances = new ArrayList<>(attendances);
    }

    @Override
    public void delete(final Attendance attendance) throws EntityNotFoundException {
        try {
            attendanceDao.delete(attendance);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Attendance> getAll() {
        try {
            return attendanceDao.getAll();
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }
}
