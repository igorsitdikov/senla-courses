package com.senla.hotel.dao;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.RoomHistoryResultSetMapperImpl;
import com.senla.hotel.mapper.interfaces.ResultSetMapper;
import com.senla.hotel.utils.Connector;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class RoomHistoryDaoImpl extends AbstractDao<RoomHistory, Long> implements RoomHistoryDao {
    public RoomHistoryDaoImpl(Connector connector) {
        super(connector);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM history JOIN resident ON resident.id = history.resident_id JOIN room ON room.id = history.room_id ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO history (room_id, resident_id, check_in, check_out) VALUES (?, ?, ?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE history SET name = ?, price = ?, status = ?, stars =?, accommodation = ? WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM history WHERE id = ?";
    }

    @Override
    protected List<RoomHistory> parseResultSet(ResultSet rs) throws PersistException {
        ResultSetMapper<RoomHistory> mapper = new RoomHistoryResultSetMapperImpl();
        LinkedList<RoomHistory> result = new LinkedList<>();
        try {
            while (rs.next()) {
                RoomHistory roomHistory = mapper.sourceToDestination(rs);
                result.add(roomHistory);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }
//
//    private RoomHistory mapResultSetToRoomHistory(ResultSet resultSet) throws SQLException {
//        RoomHistory roomHistory = new RoomHistory();
//        roomHistory.setId(resultSet.getLong("history.id"));
//        Room room = new Room();
//        room.setId(resultSet.getLong("room.id"));
//        room.setNumber(resultSet.getInt("room.number"));
//        room.setPrice(resultSet.getBigDecimal("room.price"));
//        room.setStatus(RoomStatus.valueOf(resultSet.getString("room.status")));
//        room.setStars(Stars.valueOf(resultSet.getString("room.stars")));
//        room.setAccommodation(Accommodation.valueOf(resultSet.getString("room.accommodation")));
//
//        Resident resident = new Resident();
//        resident.setId(resultSet.getLong("resident.id"));
//        resident.setFirstName(resultSet.getString("resident.first_name"));
//        resident.setLastName(resultSet.getString("resident.last_name"));
//        resident.setGender(Gender.valueOf(resultSet.getString("resident.gender")));
//        resident.setVip(resultSet.getInt("resident.vip") == 1);
//        resident.setPhone(resultSet.getString("resident.phone"));
//
//        roomHistory.setRoom(room);
//        roomHistory.setResident(resident);
//        roomHistory.setCheckIn(resultSet.getDate("history.check_in").toLocalDate());
//        roomHistory.setCheckOut(resultSet.getDate("history.check_out").toLocalDate());
//        return roomHistory;
//    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, RoomHistory object) throws PersistException {
        try {
            statement.setLong(1, object.getRoom().getId());
            statement.setLong(2, object.getResident().getId());
            statement.setDate(3, Date.valueOf(object.getCheckIn()));
            statement.setDate(4, Date.valueOf(object.getCheckOut()));
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, RoomHistory object) throws PersistException {
        try {
            statement.setLong(1, object.getRoom().getId());
            statement.setLong(2, object.getResident().getId());
            statement.setDate(3, Date.valueOf(object.getCheckIn()));
            statement.setDate(4, Date.valueOf(object.getCheckOut()));
            statement.setLong(5, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
