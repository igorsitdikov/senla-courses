package com.senla.hotel.dao.jdbc;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.resultSetMapper.RoomResultSetMapper;
import com.senla.hotel.utils.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class RoomDaoImpl extends AbstractDao<Room, Long> implements RoomDao {

    @Autowired
    private RoomResultSetMapper mapper;

    public RoomDaoImpl(final Connector connector) {
        super(connector);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT  room.id AS rm_id,\n" +
               "       room.number AS rm_number,\n" +
               "       room.price AS rm_price,\n" +
               "       room.status AS rm_status,\n" +
               "       room.stars AS rm_stars,\n" +
               "       room.accommodation AS rm_accommodation " +
               "FROM room ";
    }

    public String getCountVacantRoomsQuery() {
        return "SELECT DISTINCT COUNT(*) AS total FROM room WHERE status = 'VACANT' ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO room (number, price, status, stars, accommodation) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE room SET number = ?, price = ?, status = ?, stars =?, accommodation = ? WHERE id = ?;";
    }

    public String getVacantOnDate() {
        return "SELECT DISTINCT " +
               "       room.id AS rm_id,\n" +
               "       room.number AS rm_number,\n" +
               "       room.price AS rm_price,\n" +
               "       room.status AS rm_status,\n" +
               "       room.stars AS rm_stars,\n" +
               "       room.accommodation AS rm_accommodation," +
               "FROM history\n" +
               "   RIGHT JOIN room ON history.room_id = room.id \n" +
               "WHERE history.status = 'CHECKED_IN' " +
               "   OR room.status = 'VACANT' " +
               "   OR history.check_out < ? " +
               "ORDER BY room.number;\n";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM room WHERE id = ?";
    }

    @Override
    public List<Room> getVacantRooms(final SortField sortField) throws PersistException {
        String sql = getSelectQuery();
        sql += " WHERE status = ? " + setSortFieldToSql(sortField);
        return getAllBySqlQuery(sql, RoomStatus.VACANT);
    }

    private String setSortFieldToSql(final SortField sortField) {
        String fieldName;
        switch (sortField) {
            case ACCOMMODATION:
            case PRICE:
            case STARS:
                fieldName = sortField.getFieldName();
                break;
            default:
                fieldName = SortField.DEFAULT.getFieldName();
        }
        return String.format(" ORDER BY rm_%s ASC ", fieldName);
    }

    @Override
    public Room findByNumber(final Integer number) throws PersistException {
        return getSingleBy("number", number);
    }

    @Override
    protected List<Room> parseResultSet(final ResultSet rs) throws PersistException {
        final LinkedList<Room> result = new LinkedList<>();
        try {
            while (rs.next()) {
                final Room room = mapper.sourceToDestination(rs);
                result.add(room);
            }
        } catch (final Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    public List<Room> getVacantOnDate(final LocalDate date) throws PersistException {
        String sql = getVacantOnDate();
        return getAllBySqlQuery(sql, date);
    }

    @Override
    protected void prepareStatementForInsert(final PreparedStatement statement, final Room object)
        throws PersistException {
        try {
            statement.setInt(1, object.getNumber());
            statement.setBigDecimal(2, object.getPrice());
            statement.setString(3, object.getStatus().toString());
            statement.setString(4, object.getStars().toString());
            statement.setString(5, object.getAccommodation().toString());
        } catch (final Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(final PreparedStatement statement, final Room object)
        throws PersistException {
        try {
            statement.setInt(1, object.getNumber());
            statement.setBigDecimal(2, object.getPrice());
            statement.setString(3, object.getStatus().toString());
            statement.setString(4, object.getStars().toString());
            statement.setString(5, object.getAccommodation().toString());
            statement.setLong(6, object.getId());
        } catch (final Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public List<Room> getAllSortedBy(final SortField sortField) throws PersistException {
        final List<Room> list;
        String sql = getSelectQuery() + setSortFieldToSql(sortField);
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
    public Long countVacantRooms() throws PersistException {
        String sql = getCountVacantRoomsQuery();
        try {
            final PreparedStatement statement = connector.getConnection().prepareStatement(sql);
            final ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getLong("total");
        } catch (final Exception e) {
            throw new PersistException(e);
        }
    }
}
