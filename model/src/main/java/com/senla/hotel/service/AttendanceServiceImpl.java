package com.senla.hotel.service;


import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.csvMapper.AttendanceMapper;
import com.senla.hotel.service.interfaces.AttendanceService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

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
    @Value("${attendances:attendances.csv}")
    private String property;

    @Override
    public Attendance findById(final Long id) throws PersistException {
        return attendanceDao.findById(id);
    }

    @Override
    public void createAttendance(final Attendance attendance) throws PersistException {
        attendanceDao.create(attendance);
    }

    @Override
    public List<Attendance> showAttendances(final SortField sortField) throws PersistException {
        return attendanceDao.getAllSortedBy(sortField);
    }

    private List<Attendance> sortAttendances(final List<Attendance> attendances,
                                             final Comparator<Attendance> comparator) {
        attendances.sort(comparator);
        return attendances;
    }

    @Override
    public Attendance changeAttendancePrice(final Long id, final BigDecimal price) throws PersistException {
        final Attendance attendance = attendanceDao.findById(id);
        attendance.setPrice(price);
        return attendanceDao.update(attendance);
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
        csvWriter.write(property, ParseUtils.entitiesToCsv(attendanceMapper, showAttendances(SortField.DEFAULT)));
    }
}
