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
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Component
public class AttendanceServiceImpl implements AttendanceService {

    private final CsvReader csvReader;
    private final CsvWriter csvWriter;
    private final AttendanceDao attendanceDao;
    private final AttendanceMapper attendanceMapper;
    @Value(value = "attendances")
    private String property;

    public AttendanceServiceImpl(final CsvReader csvReader,
                                 final CsvWriter csvWriter,
                                 final AttendanceDao attendanceDao,
                                 final AttendanceMapper attendanceMapper) {
        this.csvReader = csvReader;
        this.csvWriter = csvWriter;
        this.attendanceDao = attendanceDao;
        this.attendanceMapper = attendanceMapper;
    }

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
    public void changeAttendancePrice(final Long id, final BigDecimal price) throws PersistException {
        final Attendance attendance = attendanceDao.findById(id);
        attendance.setPrice(price);
        attendanceDao.update(attendance);
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
