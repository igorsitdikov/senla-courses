package com.senla.hotel.dao.interfaces;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.exceptions.PersistException;

import java.util.List;

public interface ResidentDao extends GenericDao<Resident, Long> {
    List<Resident> getLastResidentsByRoomId(final Long roomId, final Integer limit) throws PersistException;
}
