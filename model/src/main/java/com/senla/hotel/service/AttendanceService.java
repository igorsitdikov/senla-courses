package com.senla.hotel.service;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.repository.AttendanceRepository;
import com.senla.hotel.repository.interfaces.IAttendanceRepository;
import com.senla.hotel.service.interfaces.IAttendanceService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.comparator.AttendanceNameComparator;
import com.senla.hotel.utils.comparator.AttendancePriceComparator;
import com.senla.hotel.utils.csv.reader.CsvReader;
import com.senla.hotel.utils.csv.writer.CsvWriter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AttendanceService implements IAttendanceService {
    private static final String PROPERTY = "attendances";
    private static AttendanceService attendanceService;
    private IAttendanceRepository attendanceRepository = AttendanceRepository.getInstance();

    private AttendanceService() {
    }

    public static AttendanceService getInstance() {
        if (attendanceService == null) {
            attendanceService = new AttendanceService();
        }
        return attendanceService;
    }

    @Override
    public Attendance findById(final Long id) throws EntityNotFoundException {
        return (Attendance) attendanceRepository.findById(id);
    }

    @Override
    public List<Attendance> sortAttendances(final List<Attendance> attendances,
                                            final Comparator<Attendance> comparator) {
        attendances.sort(comparator);
        return attendances;
    }

    @Override
    public void createAttendance(final Attendance attendance) {
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
    public void changeAttendancePrice(final String name, final BigDecimal price) {
        attendanceRepository.changePrice(name, price);
    }

    @Override
    public void importAttendances() {
        final List<Attendance>
                attendances = ParseUtils.stringToAttendances(CsvReader.getInstance().read(PROPERTY));
        attendanceRepository.setAttendances(attendances);
    }

    @Override
    public void exportAttendances() {
        CsvWriter.getInstance().write(PROPERTY, ParseUtils.attendancesToCsv());
    }
}
