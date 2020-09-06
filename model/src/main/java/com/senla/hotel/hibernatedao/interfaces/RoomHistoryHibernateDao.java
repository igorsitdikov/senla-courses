package com.senla.hotel.hibernatedao.interfaces;

import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.math.BigDecimal;

public interface RoomHistoryHibernateDao extends GenericHibernateDao<RoomHistory, Long> {

    RoomHistory getByResidentAndCheckedInStatus(Long id) throws PersistException, EntityNotFoundException;

    BigDecimal calculateBill(Long id) throws PersistException;

    void addAttendanceToHistory(Long historyId, Long attendanceId) throws PersistException;
}
