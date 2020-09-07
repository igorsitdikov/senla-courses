package com.senla.hotel.dao.jdbc;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.resultSetMapper.ResidentResultSetMapper;
import com.senla.hotel.mapper.interfaces.resultSetMapper.RoomHistoryResultSetMapper;
import com.senla.hotel.utils.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

//@Singleton
public class ResidentDaoImpl extends AbstractDao<Resident, Long> implements ResidentDao {

    @Autowired
    private ResidentResultSetMapper mapper;
    @Autowired
    private RoomHistoryResultSetMapper historyMapper;
    @Autowired
    private RoomHistoryDao roomHistoryDao;

    public ResidentDaoImpl(final Connector connector) {
        super(connector);
    }

    @Override
    public String getSelectQuery() {
       return getSelectQueryWithResidentAndRoom("");
    }

    private String getSelectQueryWithResidentAndRoom(String parameter) {
        return String.format("SELECT  rt_id, " +
                "       rt_first_name,\n" +
                "       rt_last_name,\n" +
                "       rt_gender,\n" +
                "       rt_vip,\n" +
                "       rt_phone,\n" +
                "       h_id,\n" +
                "       h_room_id,\n" +
                "       h_resident_id,\n" +
                "       h_check_in,\n" +
                "       h_check_out,\n" +
                "       h_status,\n" +
                "       room.id AS rm_id,\n" +
                "       room.number AS rm_number,\n" +
                "       room.price AS rm_price,\n" +
                "       room.status AS rm_status,\n" +
                "       room.stars AS rm_stars,\n" +
                "       room.accommodation AS rm_accommodation\n" +
                "FROM (\n" +
                "         SELECT DISTINCT resident.id         AS rt_id,\n" +
                "                         resident.first_name AS rt_first_name,\n" +
                "                         resident.last_name  AS rt_last_name,\n" +
                "                         resident.gender     AS rt_gender,\n" +
                "                         resident.vip        AS rt_vip,\n" +
                "                         resident.phone      AS rt_phone,\n" +
                "                         history.id          AS h_id,\n" +
                "                         history.room_id     AS h_room_id,\n" +
                "                         history.resident_id AS h_resident_id,\n" +
                "                         history.check_in    AS h_check_in,\n" +
                "                         history.check_out   AS h_check_out,\n" +
                "                         history.status      AS h_status\n" +
                "         FROM history\n" +
                "                  RIGHT JOIN resident ON resident.id = history.resident_id\n" +
                "         UNION\n" +
                "         SELECT DISTINCT resident.id         AS rt_id,\n" +
                "                         resident.first_name AS rt_first_name,\n" +
                "                         resident.last_name  AS rt_last_name,\n" +
                "                         resident.gender     AS rt_gender,\n" +
                "                         resident.vip        AS rt_vip,\n" +
                "                         resident.phone      AS rt_phone,\n" +
                "                         history.id          AS h_id,\n" +
                "                         history.room_id     AS h_room_id,\n" +
                "                         history.resident_id AS h_resident_id,\n" +
                "                         history.check_in    AS h_check_in,\n" +
                "                         history.check_out   AS h_check_out,\n" +
                "                         history.status      AS h_status\n" +
                "         FROM history\n" +
                "                  LEFT JOIN resident ON resident.id = history.resident_id\n" +
                "     ) AS a\n" +
                "         LEFT JOIN room ON a.h_room_id = room.id\n" +
                "WHERE a.rt_first_name IS NOT NULL\n" +
                "  AND (h_id IN (SELECT MAX(history.id) FROM history GROUP BY history.resident_id) OR h_id IS NULL) %s\n" +
                "GROUP BY a.rt_first_name\n" +
                "UNION\n" +
                "SELECT *\n" +
                "FROM (SELECT DISTINCT resident.id         AS rt_id,\n" +
                "                      resident.first_name AS rt_first_name,\n" +
                "                      resident.last_name  AS rt_last_name,\n" +
                "                      resident.gender     AS rt_gender,\n" +
                "                      resident.vip        AS rt_vip,\n" +
                "                      resident.phone      AS rt_phone,\n" +
                "                      history.id          AS h_id,\n" +
                "                      history.room_id     AS h_room_id,\n" +
                "                      history.resident_id AS h_resident_id,\n" +
                "                      history.check_in    AS h_check_in,\n" +
                "                      history.check_out   AS h_check_out,\n" +
                "                      history.status      AS h_status\n" +
                "      FROM history\n" +
                "               RIGHT JOIN resident ON resident.id = history.resident_id\n" +
                "      UNION\n" +
                "      SELECT DISTINCT resident.id         AS rt_id,\n" +
                "                      resident.first_name AS rt_first_name,\n" +
                "                      resident.last_name  AS rt_last_name,\n" +
                "                      resident.gender     AS rt_gender,\n" +
                "                      resident.vip        AS rt_vip,\n" +
                "                      resident.phone      AS rt_phone,\n" +
                "                      history.id          AS h_id,\n" +
                "                      history.room_id     AS h_room_id,\n" +
                "                      history.resident_id AS h_resident_id,\n" +
                "                      history.check_in    AS h_check_in,\n" +
                "                      history.check_out   AS h_check_out,\n" +
                "                      history.status      AS h_status\n" +
                "      FROM history\n" +
                "               LEFT JOIN resident ON resident.id = history.resident_id\n" +
                "     ) AS a\n" +
                "         RIGHT JOIN room ON a.h_room_id = room.id\n" +
                "WHERE a.rt_first_name IS NOT NULL\n" +
                "  AND ( h_id IN (SELECT MAX(history.id) FROM history GROUP BY history.resident_id) OR h_id IS NULL) %s", parameter, parameter);
    }

    public String getSelectQueryById(String parameter) {
        return getSelectQueryWithResidentAndRoom(parameter);
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
                "WHERE room_id = ? " +
                "ORDER BY history.check_out " +
                "DESC " +
                "LIMIT ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM resident WHERE id = ?";
    }

    @Override
    public Resident findById(final Long id) throws PersistException {
        String sql = getSelectQueryById("AND rt_id = ?");
        return getAllBySqlQuery(sql, id, id).iterator().next();
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
                final RoomHistory history = historyMapper.sourceToDestination(rs);
                Set<RoomHistory> histories = new HashSet<>();
                histories.add(history);
                resident.setHistory(histories);
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
