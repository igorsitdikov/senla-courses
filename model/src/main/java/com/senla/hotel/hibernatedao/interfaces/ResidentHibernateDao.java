package com.senla.hotel.hibernatedao.interfaces;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.exceptions.PersistException;

import java.util.List;

public interface ResidentHibernateDao extends GenericHibernateDao<Resident, Long> {

    List<Resident> getLastResidentsByRoomId(Long roomId, Integer limit) throws PersistException;
}
