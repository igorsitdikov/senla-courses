package com.senla.hotel.mapper;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.Gender;
import com.senla.hotel.mapper.interfaces.resultSetMapper.ResidentResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResidentResultSetMapperImpl implements ResidentResultSetMapper {

    @Override
    public Resident sourceToDestination(final ResultSet source) throws SQLException {
        final Resident resident = new Resident();
        resident.setId(source.getLong("rt_id"));
        if (source.wasNull()) {
            return new Resident();
        }
        resident.setFirstName(source.getString("rt_first_name"));
        resident.setLastName(source.getString("rt_last_name"));
        resident.setGender(Gender.valueOf(source.getString("rt_gender")));
        resident.setVip(source.getInt("rt_vip") == 1);
        resident.setPhone(source.getString("rt_phone"));
        return resident;
    }
}
