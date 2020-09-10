package com.senla.hotel.dao.interfaces;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;

import java.util.List;

public interface ResidentDao extends GenericDao<Resident, Long> {

    Integer countTotalResidents() throws PersistException;

    List<Resident> getAllSortedBy(SortField sortField) throws PersistException;

    List<Resident> getLastResidentsByRoomId(Long roomId, Integer limit) throws PersistException;
}
