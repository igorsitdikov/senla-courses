package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.ResidentMapperImpl;
import com.senla.hotel.mapper.interfaces.csvMapper.EntityMapper;
import com.senla.hotel.mapper.interfaces.csvMapper.ResidentMapper;
import com.senla.hotel.service.interfaces.AttendanceService;
import com.senla.hotel.service.interfaces.ResidentService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.comparator.ResidentCheckOutComparator;
import com.senla.hotel.utils.comparator.ResidentFullNameComparator;
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
    private ResidentDao residentRepository;
    @Autowired
    private RoomHistoryDao roomHistoryRepository;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private ResidentMapper residentMapper;
    @PropertyLoad(propertyName = "residents")
    private String property;

    @Override
    public Resident findById(final Long id) throws EntityNotFoundException, PersistException {
        final Resident resident = residentRepository.findById(id);
        if (resident == null) {
            throw new EntityNotFoundException(String.format("No resident with id %d%n", id));
        }
        return resident;
    }

    @Override
    public void addAttendanceToResident(final Resident resident, final Attendance attendance)
            throws EntityNotFoundException, PersistException {
        final Long residentId = resident.getId();
        final Long attendanceId = attendanceService.findById(attendance.getId()).getId();
        addAttendanceToResident(residentId, attendanceId);
    }

    @Override
    public void addAttendanceToResident(final Long residentId, final Long attendanceId) throws EntityNotFoundException, PersistException {
        final Attendance attendance = attendanceService.findById(attendanceId);
        final RoomHistory history = roomHistoryRepository
                .getByResidentAndCheckedInStatus(residentId);
        roomHistoryRepository.addAttendanceToHistory(history.getId(), attendance.getId());
    }

    @Override
    public void createResident(final Resident resident) throws PersistException {
        residentRepository.create(resident);
    }

    @Override
    public List<Resident> showResidents(final SortField sortField) throws PersistException {
        final List<Resident> residents = residentRepository.getAll();
        switch (sortField) {
            case NAME:
                return sortResidents(residents, new ResidentFullNameComparator());
            case CHECK_OUT_DATE:
                return sortResidents(residents, new ResidentCheckOutComparator());
            default:
                return residents;
        }
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
        residentRepository.insertMany(residents);
    }

    @Override
    public void exportResidents() throws PersistException {
        csvWriter.write(property, ParseUtils.entitiesToCsv(residentMapper, showResidents(SortField.DEFAULT)));
    }

    @Override
    public int showCountResidents() throws PersistException {
        return residentRepository.getAll().size();
    }

}
