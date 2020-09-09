package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dto.AttendanceDTO;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.csvMapper.AttendanceMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.AttendanceDtoMapper;
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
import java.util.stream.Collectors;

@Singleton
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
    @PropertyLoad(propertyName = "attendances")
    private String property;

    @Override
    public AttendanceDTO findById(final Long id) throws PersistException, EntityIsEmptyException {
        return attendanceDtoMapper.destinationToSource(attendanceDao.findById(id));
    }

    @Override
    public void createAttendance(final AttendanceDTO attendance) throws PersistException, EntityIsEmptyException {
        Attendance entity = attendanceDtoMapper.sourceToDestination(attendance);
        attendanceDao.create(entity);
    }

    @Override
    public List<AttendanceDTO> showAttendances(final SortField sortField) throws PersistException, EntityIsEmptyException {
        final List<Attendance> attendances = attendanceDao.getAll();
        final List<AttendanceDTO> attendancesDTO = new ArrayList<>();
        for (Attendance attendance : attendances) {
            AttendanceDTO attendanceDTO = attendanceDtoMapper.destinationToSource(attendance);
            attendancesDTO.add(attendanceDTO);
        }
        switch (sortField) {
            case PRICE:
                return sortAttendances(attendancesDTO, new AttendancePriceComparator());
            case NAME:
                return sortAttendances(attendancesDTO, new AttendanceNameComparator());
            default:
                return attendancesDTO;
        }
    }

    private List<AttendanceDTO> sortAttendances(final List<AttendanceDTO> attendances,
                                             final Comparator<AttendanceDTO> comparator) {
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
//        csvWriter.write(property, ParseUtils.entitiesToCsv(attendanceMapper, showAttendances(SortField.DEFAULT)));
    }
}
