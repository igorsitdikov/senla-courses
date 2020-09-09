package com.senla.hotel.dao.jdbc;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.resultSetMapper.AttendanceResultSetMapper;
import com.senla.hotel.utils.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class AttendanceDaoImpl extends AbstractDao<Attendance, Long> implements AttendanceDao {

    @Autowired
    private AttendanceResultSetMapper mapper;

    public AttendanceDaoImpl(final Connector connector) {
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

    public String getSelectAttendancesInHistory() {
        return "SELECT * FROM history\n" +
               "    JOIN histories_attendances ha ON history.id = ha.history_id\n" +
               "    JOIN attendance ON ha.attendance_id = attendance.id\n" +
               "WHERE history_id = ?;";
    }

    @Override
    public List<Attendance> parseResultSet(final ResultSet rs) throws PersistException {
        final LinkedList<Attendance> result = new LinkedList<>();
        try {
            while (rs.next()) {
                final Attendance attendance = mapper.sourceToDestination(rs);
                result.add(attendance);
            }
        } catch (final Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    public List<Attendance> getAllSortedBy(final SortField sortField) throws PersistException {
        final List<Attendance> list;
        final String sql = getSelectQuery() + " ORDER BY " + sortField.getFieldName() + " ASC ";
        try {
            final PreparedStatement statement = connector.getConnection().prepareStatement(sql);
            final ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (final Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    @Override
    public List<Attendance> getAllAttendancesByHistoryId(final Long id) throws PersistException {
        final String sql = getSelectAttendancesInHistory();
        return getAllBySqlQuery(sql, id);
    }

    @Override
    protected void prepareStatementForInsert(final PreparedStatement statement, final Attendance object)
        throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setBigDecimal(2, object.getPrice());
        } catch (final Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(final PreparedStatement statement, final Attendance object)
        throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setBigDecimal(2, object.getPrice());
            statement.setLong(3, object.getId());
        } catch (final Exception e) {
            throw new PersistException(e);
        }
    }
}
