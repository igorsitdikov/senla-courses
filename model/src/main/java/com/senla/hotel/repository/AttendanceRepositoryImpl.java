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
import com.senla.hotel.utils.Connector;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class AttendanceRepositoryImpl implements AttendanceRepository {
    @Autowired
    private Connector connector;
    @Autowired
    private AttendanceDao attendanceDao;
    private static List<Attendance> attendances = new LinkedList<>();
    private static Long counter = 0L;

    @Override
    public AEntity add(final AEntity entity) throws EntityAlreadyExistsException {
        if (!AttendanceRepositoryImpl.attendances.contains(entity)) {
            entity.setId(++counter);
            attendances.add((Attendance) entity);
            return entity;
        }
        throw new EntityAlreadyExistsException("Attendance already exists");
    }

    @Override
    public void changePrice(final Long id, final BigDecimal price) throws EntityNotFoundException {
        final Attendance attendance = (Attendance) findById(id);
        attendance.setPrice(price);
    }

    @Override
    public void changePrice(final String name, final BigDecimal price) {
        final Attendance attendance = (Attendance) findByName(name);
        attendance.setPrice(price);
    }

    @Override
    public AEntity findById(final Long id) throws EntityNotFoundException {
        for (final Attendance attendance : attendances) {
            if (attendance.getId().equals(id)) {
                return attendance;
            }
        }
        throw new EntityNotFoundException(String.format("No attendance with id %d%n", id));
    }

    @Override
    public AEntity findByName(final String name) {
        for (final Attendance attendance : attendances) {
            if (attendance.getName().equals(name)) {
                return attendance;
            }
        }
        return null;
    }

    @Override
    public void setAttendances(final List<Attendance> attendances) {
        AttendanceRepositoryImpl.attendances.clear();
        AttendanceRepositoryImpl.attendances = new ArrayList<>(attendances);
    }

    @Override
    public void deleteAttendance(final Long id) throws EntityNotFoundException {
        final Attendance attendance = (Attendance) findById(id);
        AttendanceRepositoryImpl.attendances.remove(attendance);
    }

    @Override
    public List<Attendance> getAttendances() {
        try {
            List<Attendance> all = attendanceDao.getAll();
            return all;
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

    private Attendance parseResultSet(ResultSet resultSet) throws SQLException {
        Attendance attendance = new Attendance();
        attendance.setId(resultSet.getLong("id"));
        attendance.setName(resultSet.getString("name"));
        attendance.setPrice(resultSet.getBigDecimal("price"));
        return attendance;
    }

    private List<Attendance> parseResultSetToList(ResultSet resultSet) throws SQLException {
        LinkedList<Attendance> result = new LinkedList<>();
        try {
            while (resultSet.next()) {
                Attendance attendance = parseResultSet(resultSet);
                result.add(attendance);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }


}
