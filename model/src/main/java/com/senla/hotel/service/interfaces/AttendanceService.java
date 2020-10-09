package com.senla.hotel.service.interfaces;

import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;

import java.math.BigDecimal;
import java.util.List;

public interface AttendanceService {

    AttendanceDto findById(Long id) throws EntityNotFoundException, PersistException;

    void createAttendance(AttendanceDto attendance) throws EntityAlreadyExistsException, PersistException;

    void updateAttendance(AttendanceDto attendanceDto) throws PersistException;

    List<AttendanceDto> showAttendances(SortField sortField) throws PersistException;

    AttendanceDto changeAttendancePrice(Long id, BigDecimal price) throws EntityNotFoundException, PersistException;

    void delete(Long id) throws EntityNotFoundException, PersistException;

    void importAttendances() throws PersistException;

    void exportAttendances() throws PersistException;
}
