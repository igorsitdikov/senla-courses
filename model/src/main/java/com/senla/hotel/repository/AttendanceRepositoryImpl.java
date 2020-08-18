package com.senla.hotel.repository;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.AttendanceDao;
import com.senla.hotel.dao.PersistException;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.repository.interfaces.AttendanceRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class AttendanceRepositoryImpl implements AttendanceRepository {
    @Autowired
    private AttendanceDao attendanceDao;
    private static List<Attendance> attendances = new LinkedList<>();
    private static Long counter = 0L;

    @Override
    public AEntity add(final AEntity entity) throws EntityAlreadyExistsException {
        try {
           return attendanceDao.create((Attendance) entity);
        } catch (PersistException e) {
            e.printStackTrace();
        }
//        if (!AttendanceRepositoryImpl.attendances.contains(entity)) {
//            entity.setId(++counter);
//            attendances.add((Attendance) entity);
//            return entity;
//        }
        throw new EntityAlreadyExistsException("Attendance already exists");
    }

    @Override
    public void changePrice(final Long id, final BigDecimal price) throws EntityNotFoundException {
        Attendance attendance = (Attendance) findById(id);
        attendance.setPrice(price);
        try {
            attendanceDao.update(attendance);
        } catch (PersistException e) {
            e.printStackTrace();
        }
//        attendance.setPrice(price);
    }

//    @Override
//    public void changePrice(final String name, final BigDecimal price) {
//        Attendance attendance = null;
//        attendance = (Attendance) findByName(name);
//        attendance.setPrice(price);
//        try {
//            attendanceDao.update(attendance);
//        } catch (PersistException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public AEntity findById(final Long id) throws EntityNotFoundException {
        try {
            return attendanceDao.getById(id);
        } catch (PersistException e) {
            throw new EntityNotFoundException(String.format("No attendance with id %d%n", id));
        }
    }

//    @Override
//    public AEntity findByName(final String name) {
//        try {
//            return attendanceDao.getByName(name);
//        } catch (PersistException e) {
//            e.printStackTrace();
//        }
//        return null;
////        for (final Attendance attendance : attendances) {
////            if (attendance.getName().equals(name)) {
////                return attendance;
////            }
////        }
////        return null;
//    }

    @Override
    public void setAttendances(final List<Attendance> attendances) {
        AttendanceRepositoryImpl.attendances.clear();
        AttendanceRepositoryImpl.attendances = new ArrayList<>(attendances);
    }

    @Override
    public void deleteAttendance(final Long id) throws EntityNotFoundException {
        final Attendance attendance = (Attendance) findById(id);
        try {
            attendanceDao.delete(attendance);
        } catch (PersistException e) {
            e.printStackTrace();
        }
//        AttendanceRepositoryImpl.attendances.remove(attendance);
    }

    @Override
    public List<Attendance> getAttendances() {
        try {
            return attendanceDao.getAll();
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
//        List<Attendance> list = new LinkedList<>();
//        String sql = "SELECT * FROM attendance;";
//        try (PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
//            ResultSet rs = statement.executeQuery();
//            list = parseResultSetToList(rs);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
//        return list;
    }

    @Override
    public void setCounter(final Long counter) {
        AttendanceRepositoryImpl.counter = counter;
    }
}
