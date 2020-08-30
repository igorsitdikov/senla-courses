package com.senla.hotel.mapper;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.mapper.interfaces.resultSetMapper.ResultSetMapper;
import com.senla.hotel.mapper.interfaces.resultSetMapper.RoomHistoryResultSetMapper;
import com.senla.hotel.mapper.interfaces.resultSetMapper.RoomResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomHistoryResultSetMapperImpl implements RoomHistoryResultSetMapper {

    @Autowired
    private RoomResultSetMapper roomResultSetMapper;
    @Autowired
    private RoomResultSetMapper residentResultSetMapper;

    @Override
    public RoomHistory sourceToDestination(final ResultSet source) throws SQLException {
        final ResultSetMapper<Resident> residentResultSetMapper = new ResidentResultSetMapperImpl();

        final RoomHistory roomHistory = new RoomHistory();
        roomHistory.setId(source.getLong("h_id"));
        if (source.wasNull()) {
            return new RoomHistory();
        }

        final Room room = roomResultSetMapper.sourceToDestination(source);
        final Resident resident = residentResultSetMapper.sourceToDestination(source);

        roomHistory.setRoom(room);
        roomHistory.setResident(resident);
        roomHistory.setCheckIn(source.getDate("h_check_in").toLocalDate());
        roomHistory.setCheckOut(source.getDate("h_check_out").toLocalDate());
        roomHistory.setStatus(HistoryStatus.valueOf(source.getString("h_status")));
        return roomHistory;
    }
}
