package com.senla.hotel.dao;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class AttendanceDaoImpl extends AbstractDao<Attendance, Long> implements AttendanceDao {
    public AttendanceDaoImpl(Connector connector) {
        super(connector);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM attendance ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO attendance (name, price) VALUES (?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE attendance SET name = ?, price = ? WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM attendance WHERE id = ?";
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
        try {
            statement.setString(1, object.getName());
            statement.setBigDecimal(2, object.getPrice());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Attendance object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setBigDecimal(2, object.getPrice());
            statement.setLong(3, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
