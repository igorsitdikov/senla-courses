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
        room.setId(source.getLong("room.id"));
        room.setNumber(source.getInt("room.number"));
        room.setPrice(source.getBigDecimal("room.price"));
        room.setStatus(RoomStatus.valueOf(source.getString("room.status")));
        room.setStars(Stars.valueOf(source.getString("room.stars")));
        room.setAccommodation(Accommodation.valueOf(source.getString("room.accommodation")));
        return room;
    }
}
