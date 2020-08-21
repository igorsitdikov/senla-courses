package com.senla.hotel.dao;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.resultSetMapper.ResidentResultSetMapper;
import com.senla.hotel.utils.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class ResidentDaoImpl extends AbstractDao<Resident, Long> implements ResidentDao {
    @Autowired
    private ResidentResultSetMapper mapper;
    @Autowired
    private RoomHistoryDao roomHistoryDao;

    public ResidentDaoImpl(final Connector connector) {
        super(connector);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM resident ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO resident (first_name, last_name, gender, vip, phone) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE resident SET first_name = ?, last_name = ?, gender = ?, vip =?, phone = ? WHERE id = ?;";
    }

    public String getLastResidentsByRoomIdQuery() {
        return "SELECT * FROM history\n" +
                "    JOIN resident on resident.id = history.resident_id\n" +
                "WHERE room_id = ? ORDER BY history.check_out DESC LIMIT ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM resident WHERE id = ?";
    }

    @Override
    public List<Resident> getLastResidentsByRoomId(final Long roomId, final Integer limit) throws PersistException {
        String sql = getLastResidentsByRoomIdQuery();
        return getAllBySqlQuery(sql, roomId, limit);
    }

    @Override
    protected List<Resident> parseResultSet(final ResultSet rs) throws PersistException {
        final LinkedList<Resident> result = new LinkedList<>();
        try {
            while (rs.next()) {
                final Resident resident = mapper.sourceToDestination(rs);
                final RoomHistory history = roomHistoryDao.getByResidentAndCheckedInStatus(resident.getId());
                resident.setHistory(history);
                result.add(resident);
            }
        } catch (final Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(final PreparedStatement statement, final Resident object) throws PersistException {
        try {
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getLastName());
            statement.setString(3, object.getGender().toString());
            statement.setInt(4, object.getVip() ? 1 : 0);
            statement.setString(5, object.getPhone());
        } catch (final Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(final PreparedStatement statement, final Resident object) throws PersistException {
        try {
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getLastName());
            statement.setString(3, object.getGender().toString());
            statement.setInt(4, object.getVip() ? 1 : 0);
            statement.setString(5, object.getPhone());
            statement.setLong(6, object.getId());
        } catch (final Exception e) {
            throw new PersistException(e);
        }
    }
}
