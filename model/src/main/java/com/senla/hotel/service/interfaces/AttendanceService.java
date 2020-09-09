package com.senla.hotel.service.interfaces;

import com.senla.hotel.dto.AttendanceDTO;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.math.BigDecimal;
import java.util.List;

public interface AttendanceService {

    AttendanceDTO findById(Long id) throws EntityNotFoundException, PersistException, EntityIsEmptyException;

    void createAttendance(AttendanceDTO attendance) throws EntityAlreadyExistsException, PersistException, EntityIsEmptyException;

    List<AttendanceDTO> showAttendances(SortField sortField) throws PersistException, EntityIsEmptyException;

    void changeAttendancePrice(Long id, BigDecimal price) throws EntityNotFoundException, PersistException;

    void delete(Long id) throws EntityNotFoundException, PersistException;

    void importAttendances() throws PersistException;

    void exportAttendances() throws PersistException;
}
