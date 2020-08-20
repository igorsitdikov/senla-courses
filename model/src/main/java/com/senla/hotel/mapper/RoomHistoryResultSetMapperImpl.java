package com.senla.hotel.mapper;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.mapper.interfaces.ResultSetMapper;
import com.senla.hotel.mapper.interfaces.RoomHistoryResultSetMapper;
import com.senla.hotel.mapper.interfaces.RoomResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomHistoryResultSetMapperImpl implements RoomHistoryResultSetMapper {
    @Autowired
    private RoomResultSetMapper roomResultSetMapper;
    @Autowired
    private RoomResultSetMapper residentResultSetMapper;

    @Override
    public RoomHistory sourceToDestination(ResultSet source) throws SQLException {
        ResultSetMapper<Resident> residentResultSetMapper = new ResidentResultSetMapperImpl();

        RoomHistory roomHistory = new RoomHistory();
        roomHistory.setId(source.getLong("history.id"));

        Room room = roomResultSetMapper.sourceToDestination(source);
        Resident resident = residentResultSetMapper.sourceToDestination(source);

        roomHistory.setRoom(room);
        roomHistory.setResident(resident);
        roomHistory.setCheckIn(source.getDate("history.check_in").toLocalDate());
        roomHistory.setCheckOut(source.getDate("history.check_out").toLocalDate());
        roomHistory.setStatus(HistoryStatus.valueOf(source.getString("history.status")));
        return roomHistory;
    }
}
