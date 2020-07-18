package com.senla.hotel.service;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.PropertyLoad;
import com.senla.anntotaion.Singleton;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.mapper.AttendanceMapper;
import com.senla.hotel.mapper.interfaces.IEntityMapper;
import com.senla.hotel.repository.interfaces.IAttendanceRepository;
import com.senla.hotel.service.interfaces.IAttendanceService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.comparator.AttendanceNameComparator;
import com.senla.hotel.utils.comparator.AttendancePriceComparator;
import com.senla.hotel.utils.csv.interfaces.ICsvReader;
import com.senla.hotel.utils.csv.interfaces.ICsvWriter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Singleton
public class AttendanceService implements IAttendanceService {
    @Autowired
    private ICsvReader csvReader;
    @Autowired
    private ICsvWriter csvWriter;
    @Autowired
    private IAttendanceRepository attendanceRepository;
    @PropertyLoad(propertyName = "attendances")
    private String property;

    @Override
    public Attendance findById(final Long id) throws EntityNotFoundException {
        return (Attendance) attendanceRepository.findById(id);
    }

    @Override
    public Attendance findByName(final String name) {
        return (Attendance) attendanceRepository.findByName(name);
    }

    @Override
    public List<Attendance> sortAttendances(final List<Attendance> attendances,
                                            final Comparator<Attendance> comparator) {
        attendances.sort(comparator);
        return attendances;
    }

    @Override
    public void createAttendance(final Attendance attendance) throws EntityAlreadyExistsException {
        attendanceRepository.add(attendance);
    }

    @Override
    public List<Attendance> showAttendances() {
        return attendanceRepository.getAttendances();
    }

    @Override
    public List<Attendance> showAttendancesSortedByName() {
        final List<Attendance> attendances = new ArrayList<>(showAttendances());
        return sortAttendances(attendances, new AttendanceNameComparator());
    }

    @Override
    public List<Attendance> showAttendancesSortedByPrice() {
        final List<Attendance> attendances = new ArrayList<>(showAttendances());
        return sortAttendances(attendances, new AttendancePriceComparator());
    }

    @Override
    public void changeAttendancePrice(final Long id, final BigDecimal price) throws EntityNotFoundException {
        attendanceRepository.changePrice(id, price);
    }

    @Override
    public List<AEntity> add(final List<Attendance> attendances, final AEntity entity) {
        final List<AEntity> result = new ArrayList<>(attendances);
        result.add(entity);
        return result;
    }

    @Override
    public void delete(final Long id) throws EntityNotFoundException {
        attendanceRepository.deleteAttendance(id);
    }

    @Override
    public void changeAttendancePrice(final String name, final BigDecimal price) {
        attendanceRepository.changePrice(name, price);
    }

    @Override
    public void importAttendances() {
        IEntityMapper<Attendance> attendanceMapper = new AttendanceMapper();
        final List<Attendance> attendances =
            ParseUtils.stringToEntities(csvReader.read(property), attendanceMapper, Attendance.class);
        attendanceRepository.setAttendances(attendances);
    }

    @Override
    public void exportAttendances() {
        IEntityMapper<Attendance> attendanceMapper = new AttendanceMapper();
        csvWriter.write(property, ParseUtils.entitiesToCsv(attendanceMapper, showAttendances()));
    }
}
