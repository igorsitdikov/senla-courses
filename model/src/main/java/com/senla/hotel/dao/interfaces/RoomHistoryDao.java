package com.senla.hotel.dao.interfaces;

import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.PersistException;

public interface RoomHistoryDao extends GenericDao<RoomHistory, Long> {
    RoomHistory getByResidentAndCheckedInStatus(final Long id) throws PersistException;

    void addAttendanceToHistory(final Long historyId, final Long attendanceId) throws PersistException;
}
