package com.senla.hotel.dao;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.utils.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class AttendanceDao extends AbstractDao<Attendance, Long> {
    public AttendanceDao() {
    }

    public AttendanceDao(Connector connector) {
        super(connector);
    }
//    @Autowired
//    protected Connector connector;

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM attendance;";
    }

    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getDeleteQuery() {
        return null;
    }

    @Override
    protected List<Attendance> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Attendance> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Attendance attendance = mapResultSetToAttendance(rs);
                result.add(attendance);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    private Attendance mapResultSetToAttendance(ResultSet resultSet) throws SQLException {
        Attendance attendance = new Attendance();
        attendance.setId(resultSet.getLong("id"));
        attendance.setName(resultSet.getString("name"));
        attendance.setPrice(resultSet.getBigDecimal("price"));
        return attendance;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Attendance object) throws PersistException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Attendance object) throws PersistException {

    }

    @Override
    public Attendance create() {
        return null;
    }
}
