package com.senla.hotel.mapper.interfaces.resultSetMapper;

import com.senla.hotel.entity.AEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper<T extends AEntity> {
    T sourceToDestination(ResultSet source) throws SQLException;
}
