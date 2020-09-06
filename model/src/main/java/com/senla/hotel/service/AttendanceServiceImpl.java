package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.hibernatedao.interfaces.AttendanceHibernateDao;
import com.senla.hotel.hibernatedao.interfaces.ResidentHibernateDao;
import com.senla.hotel.mapper.interfaces.csvMapper.AttendanceMapper;
import com.senla.hotel.service.interfaces.AttendanceService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.comparator.AttendanceNameComparator;
import com.senla.hotel.utils.comparator.AttendancePriceComparator;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

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
    private AttendanceHibernateDao attendanceHibernateDao;
    @Autowired
    private ResidentHibernateDao residentHibernateDao;
    @PropertyLoad(propertyName = "attendances")
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
//        List<Attendance> data = attendanceHibernateDao.getAll();
////        System.out.println();
////        Attendance attendance = attendanceHibernateDao.findById(3L);
////        attendance.setName("LOL");
////        Attendance newAttendance = new Attendance();
////        newAttendance.setName("New attenfdance");
////        newAttendance.setPrice(BigDecimal.valueOf(234.45));
////        attendanceHibernateDao.create(newAttendance);
////        data = attendanceHibernateDao.getAll();
////        attendanceHibernateDao.delete(newAttendance);
////        attendanceHibernateDao.update(attendance);
////        data = attendanceHibernateDao.getAll();

        List<Resident> lastResidents = residentHibernateDao.getLastResidentsByRoomId(1L, 3);
        System.out.println();

        final List<Attendance> attendances = attendanceDao.getAll();
        switch (sortField) {
            case PRICE:
                return sortAttendances(attendances, new AttendancePriceComparator());
            case NAME:
                return sortAttendances(attendances, new AttendanceNameComparator());
            default:
                return attendances;
        }
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
