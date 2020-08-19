package com.senla.hotel.dao;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.Gender;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class ResidentDaoImpl extends AbstractDao<Resident, Long> implements ResidentDao {
    public ResidentDaoImpl(Connector connector) {
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

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM resident WHERE id = ?";
    }


    @Override
    protected List<Resident> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Resident> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Resident resident = mapResultSetToRoom(rs);
                result.add(resident);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    private Resident mapResultSetToRoom(ResultSet resultSet) throws SQLException {
        Resident resident = new Resident();
        resident.setId(resultSet.getLong("id"));
        resident.setFirstName(resultSet.getString("first_name"));
        resident.setLastName(resultSet.getString("last_name"));
        resident.setGender(Gender.valueOf(resultSet.getString("gender")));
        resident.setVip(resultSet.getInt("vip") == 1);
        resident.setPhone(resultSet.getString("phone"));
        return resident;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Resident object) throws PersistException {
        try {
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getLastName());
            statement.setString(3, object.getGender().toString());
            statement.setInt(4, object.getVip() ? 1 : 0);
            statement.setString(5, object.getPhone());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Resident object) throws PersistException {
        try {
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getLastName());
            statement.setString(3, object.getGender().toString());
            statement.setInt(4, object.getVip() ? 1 : 0);
            statement.setString(5, object.getPhone());
            statement.setLong(6, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
