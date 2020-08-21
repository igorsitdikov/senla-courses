package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.AttendanceMapperImpl;
import com.senla.hotel.mapper.interfaces.csvMapper.EntityMapper;
import com.senla.hotel.service.interfaces.AttendanceService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.comparator.AttendanceNameComparator;
import com.senla.hotel.utils.comparator.AttendancePriceComparator;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Singleton
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private CsvReader csvReader;
    @Autowired
    private CsvWriter csvWriter;
    @Autowired
    private AttendanceDao attendanceRepository;
    @PropertyLoad(propertyName = "attendances")
    private String property;

    @Override
    public Attendance findById(final Long id) throws PersistException {
        return attendanceRepository.findById(id);
    }

//    @Override
//    public Attendance findByName(final String name) {
//        return (Attendance) attendanceRepository.findByName(name);
//    }

    @Override
    public List<Attendance> sortAttendances(final List<Attendance> attendances,
                                            final Comparator<Attendance> comparator) {
        attendances.sort(comparator);
        return attendances;
    }

    @Override
    public void createAttendance(final Attendance attendance) throws PersistException {
        attendanceRepository.create(attendance);
    }

    @Override
    public List<Attendance> showAttendances(final SortField sortField) throws PersistException {
        final List<Attendance> attendances = attendanceRepository.getAll();
        switch (sortField) {
            case PRICE: return sortAttendances(attendances, new AttendancePriceComparator());
            case NAME: return sortAttendances(attendances, new AttendanceNameComparator());
        }
        return attendances;
    }

    @Override
    public void changeAttendancePrice(final Long id, final BigDecimal price) throws PersistException {
        final Attendance attendance = attendanceRepository.findById(id);
        attendance.setPrice(price);
        attendanceRepository.update(attendance);
    }

//    @Override
//    public List<AEntity> add(final List<Attendance> attendances, final AEntity entity) {
//        final List<AEntity> result = new ArrayList<>(attendances);
//        result.add(entity);
//        return result;
//    }

    @Override
    public void delete(final Long id) throws PersistException {
        final Attendance attendance = attendanceRepository.findById(id);
        attendanceRepository.delete(attendance);
    }

//    @Override
//    public void changeAttendancePrice(final String name, final BigDecimal price) {
//        attendanceRepository.changePrice(name, price);
//    }

    @Override
    public void importAttendances() {
//        EntityMapper<Attendance> attendanceMapper = new AttendanceMapperImpl();
//        final List<Attendance> attendances =
//            ParseUtils.stringToEntities(csvReader.read(property), attendanceMapper, Attendance.class);
//        attendanceRepository.setAttendances(attendances);
    }

    @Override
    public void exportAttendances() throws PersistException {
        final EntityMapper<Attendance> attendanceMapper = new AttendanceMapperImpl();
        csvWriter.write(property, ParseUtils.entitiesToCsv(attendanceMapper, showAttendances(SortField.DEFAULT)));
    }
}
