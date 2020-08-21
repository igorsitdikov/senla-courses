package com.senla.hotel.dao;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.resultSetMapper.RoomHistoryResultSetMapper;
import com.senla.hotel.utils.Connector;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class RoomHistoryDaoImpl extends AbstractDao<RoomHistory, Long> implements RoomHistoryDao {
    @Autowired
    private RoomHistoryResultSetMapper mapper;

    @Autowired
    private AttendanceDao attendanceDao;

    public RoomHistoryDaoImpl(final Connector connector) {
        super(connector);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT history.id, room.id, resident.id, history.resident_id, history.room_id, history.check_in, history.check_out, history.status, " +
                "resident.first_name, resident.last_name, resident.gender, resident.vip, resident.phone, room.number, " +
                "room.price, room.status, room.stars, room.accommodation " +
                "FROM history JOIN resident ON resident.id = history.resident_id JOIN room ON room.id = history.room_id ";
//        return "SELECT * FROM history JOIN resident ON resident.id = history.resident_id JOIN room ON room.id = history.room_id ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO history (room_id, resident_id, check_in, check_out, status) VALUES (?, ?, ?, ?, ?)";
    }

    public String getCreateQueryAttendanceToHistory() {
        return "INSERT INTO histories_attendances (history_id, attendance_id) VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE history SET room_id = ?, resident_id = ?, check_in = ?, check_out = ?, status = ? WHERE id = ?;";
    }

    public String getCalculateBillQuery() {
        return "\n" +
                "SELECT (SUM(DATEDIFF(history.check_out, history.check_in) * attendance.price) + DATEDIFF(check_out, check_in) * room.price) AS result FROM history\n" +
                "                  LEFT JOIN resident ON resident.id = history.resident_id\n" +
                "                  LEFT JOIN room ON room.id = history.room_id\n" +
                "                  LEFT JOIN histories_attendances on history.id = histories_attendances.history_id\n" +
                "                  LEFT JOIN attendance on histories_attendances.attendance_id = attendance.id\n" +
                "WHERE history.id = ? AND history.status = 'CHECKED_IN';";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM history WHERE id = ?";
    }

    @Override
    protected List<RoomHistory> parseResultSet(final ResultSet rs) throws PersistException {
        final LinkedList<RoomHistory> result = new LinkedList<>();
        try {
            while (rs.next()) {
                final RoomHistory roomHistory = mapper.sourceToDestination(rs);
                final List<Attendance> attendances = attendanceDao.getAllAttendancesByHistoryId(roomHistory.getId());
                roomHistory.setAttendances(attendances);
                result.add(roomHistory);
            }
        } catch (final Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    public RoomHistory getByResidentAndCheckedInStatus(final Long id) throws PersistException, EntityNotFoundException {
        final List<RoomHistory> list;
        String sql = getSelectQuery();
        sql += " WHERE history.status = 'CHECKED_IN' AND resident.id = ?";
        list = getAllBySqlQuery(sql, id);
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }

    @Override
    public RoomHistory findById(final Long id) throws PersistException {
        return getBy("history.id", id);
    }

    @Override
    public BigDecimal calculateBill(final Long id) throws PersistException {
        final List<Double> result = new LinkedList<>();
        final String sql = getCalculateBillQuery();
        System.out.println("id " + id);
        try (final PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
            setVariableToStatement(statement, id);
            final ResultSet rs = statement.executeQuery();
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
//                for (int i = 1; i <= columnsNumber; i++) {
//                    if (i > 1) System.out.print(",  ");
//                    String columnValue = rs.getString(i);
//                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
//                }
//                System.out.println("");
                result.add(rs.getDouble("result"));
            }
        } catch (final Exception e) {
            throw new PersistException(e);
        }
        if (result.size() == 0) {
            throw new PersistException("Received no records.");
        }
        if (result.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return BigDecimal.valueOf(result.iterator().next());
    }

    @Override
    public void addAttendanceToHistory(final Long historyId, final Long attendanceId) throws PersistException {
        final String sql = getCreateQueryAttendanceToHistory();
        try (final PreparedStatement statement = connector.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setVariableToStatement(statement, historyId, attendanceId);
            final int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On persist modify more then 1 record: " + count);
            }
        } catch (final SQLIntegrityConstraintViolationException e) {
            throw new PersistException("Attendance already added!");
        } catch (final Exception e) {
            throw new PersistException(e.getMessage());
        }
    }

    @Override
    protected void prepareStatementForInsert(final PreparedStatement statement, final RoomHistory object) throws PersistException {
        try {
            statement.setLong(1, object.getRoom().getId());
            statement.setLong(2, object.getResident().getId());
            statement.setDate(3, Date.valueOf(object.getCheckIn()));
            statement.setDate(4, Date.valueOf(object.getCheckOut()));
            statement.setString(5, object.getStatus().toString());
        } catch (final Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(final PreparedStatement statement, final RoomHistory object) throws PersistException {
        try {
            statement.setLong(1, object.getRoom().getId());
            statement.setLong(2, object.getResident().getId());
            statement.setDate(3, Date.valueOf(object.getCheckIn()));
            statement.setDate(4, Date.valueOf(object.getCheckOut()));
            statement.setString(5, object.getStatus().toString());
            statement.setLong(6, object.getId());
        } catch (final Exception e) {
            throw new PersistException(e);
        }
    }
}
