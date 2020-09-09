package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.csvMapper.ResidentMapper;
import com.senla.hotel.service.interfaces.ResidentService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Singleton
public class ResidentServiceImpl implements ResidentService {

    @Autowired
    private CsvReader csvReader;
    @Autowired
    private CsvWriter csvWriter;
    @Autowired
    private ResidentDao residentDao;
    @Autowired
    private RoomHistoryDao roomHistoryDao;
    @Autowired
    private AttendanceDao attendanceDao;
    @Autowired
    private ResidentMapper residentMapper;
    @PropertyLoad(propertyName = "residents")
    private String property;

    @Override
    public Resident findById(final Long id) throws EntityNotFoundException, PersistException {
        final Resident resident = residentDao.findById(id);
        if (resident == null) {
            throw new EntityNotFoundException(String.format("No resident with id %d%n", id));
        }
        return resident;
    }

    @Override
    public void addAttendanceToResident(final Resident resident, final Attendance attendance)
            throws EntityNotFoundException, PersistException {
        final Long residentId = resident.getId();
        final Long attendanceId = attendanceDao.findById(attendance.getId()).getId();
        addAttendanceToResident(residentId, attendanceId);
    }

    @Override
    public void addAttendanceToResident(final Long residentId, final Long attendanceId) throws EntityNotFoundException, PersistException {
        final Attendance attendance = attendanceDao.findById(attendanceId);
        final RoomHistory history = roomHistoryDao
                .getByResidentAndCheckedInStatus(residentId);
        roomHistoryDao.addAttendanceToHistory(history, attendance);
    }

    @Override
    public void createResident(final Resident resident) throws PersistException {
        residentDao.create(resident);
    }

    @Override
    public List<Resident> showResidents(final SortField sortField) throws PersistException {
        return residentDao.getAllSortedBy(sortField);
    }

    private List<Resident> sortResidents(final List<Resident> residents, final Comparator<Resident> comparator) {
        final List<Resident> result = new ArrayList<>(residents);
        result.sort(comparator);
        return result;
    }

    @Override
    public void importResidents() throws PersistException {
        final List<Resident> residents =
                ParseUtils.stringToEntities(csvReader.read(property), residentMapper, Resident.class);
        residentDao.insertMany(residents);
    }

    @Override
    public void exportResidents() throws PersistException {
        csvWriter.write(property, ParseUtils.entitiesToCsv(residentMapper, showResidents(SortField.DEFAULT)));
    }

    @Override
    public Long showCountResidents() throws PersistException {
        return residentDao.countTotalResidents();
    }
}
