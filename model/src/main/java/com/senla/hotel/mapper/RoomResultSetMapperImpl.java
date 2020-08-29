package com.senla.hotel.mapper;

import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.mapper.interfaces.resultSetMapper.RoomResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomResultSetMapperImpl implements RoomResultSetMapper {
    @Override
    public Room sourceToDestination(final ResultSet source) throws SQLException {
        final Room room = new Room();
        room.setId(source.getLong("rm_id"));
        if (source.wasNull()) {
            return new Room();
        }
        room.setNumber(source.getInt("rm_number"));
        room.setPrice(source.getBigDecimal("rm_price"));
        room.setStatus(RoomStatus.valueOf(source.getString("rm_status")));
        room.setStars(Stars.valueOf(source.getString("rm_stars")));
        room.setAccommodation(Accommodation.valueOf(source.getString("rm_accommodation")));
        return room;
    }
}
