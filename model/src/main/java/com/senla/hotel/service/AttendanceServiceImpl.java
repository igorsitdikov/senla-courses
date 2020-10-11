package com.senla.hotel.service;


import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.csvMapper.AttendanceMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.AttendanceDtoMapper;
import com.senla.hotel.service.interfaces.AttendanceService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private CsvReader csvReader;
    @Autowired
    private CsvWriter csvWriter;
    @Autowired
    private AttendanceDao attendanceDao;
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private AttendanceDtoMapper attendanceDtoMapper;
    @Value("${attendances}")
    private String property;

    @Override
    public AttendanceDto findById(final Long id) throws PersistException {
        final Attendance attendance = attendanceDao.findById(id);
        return attendanceDtoMapper.destinationToSource(attendance);
    }

    @Override
    public void createAttendance(final AttendanceDto attendanceDto) throws PersistException {
        Attendance attendance = attendanceDtoMapper.sourceToDestination(attendanceDto);
        attendanceDao.create(attendance);
    }

    @Override
    public void updateAttendance(final AttendanceDto attendanceDto) throws PersistException {
        Attendance attendance = attendanceDtoMapper.sourceToDestination(attendanceDto);
        attendanceDao.update(attendance);
    }

    @Override
    public List<AttendanceDto> showAttendances(final SortField sortField) throws PersistException {
        final List<Attendance> attendances = attendanceDao.getAllSortedBy(sortField);
        return attendances.stream().map(attendanceDtoMapper::destinationToSource).collect(Collectors.toList());
    }

    @Override
    public AttendanceDto changeAttendancePrice(final Long id, final BigDecimal price) throws PersistException {
        final Attendance attendance = attendanceDao.findById(id);
        attendance.setPrice(price);
        final Attendance updatedAttendance = attendanceDao.update(attendance);
        return attendanceDtoMapper.destinationToSource(updatedAttendance);
    }

    @Override
    public void delete(final Long id) throws PersistException {
        final Attendance attendance = attendanceDao.findById(id);
        attendanceDao.delete(attendance);
    }

    @Override
    public void importAttendances() throws PersistException {
        final List<Attendance> attendances =
            ParseUtils.stringToEntities(csvReader.read(property), attendanceMapper, Attendance.class);
        attendanceDao.insertMany(attendances);
    }

    @Override
    public void exportAttendances() throws PersistException {
        csvWriter.write(property,
                        ParseUtils.entitiesToCsv(attendanceMapper, attendanceDao.getAllSortedBy(SortField.DEFAULT)));
    }
}
