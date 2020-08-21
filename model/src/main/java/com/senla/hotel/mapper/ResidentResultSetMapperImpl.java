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
        resident.setId(source.getLong("resident.id"));
        resident.setFirstName(source.getString("resident.first_name"));
        resident.setLastName(source.getString("resident.last_name"));
        resident.setGender(Gender.valueOf(source.getString("resident.gender")));
        resident.setVip(source.getInt("resident.vip") == 1);
        resident.setPhone(source.getString("resident.phone"));
        return resident;
    }
}
