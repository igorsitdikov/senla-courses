package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.dto.AttendanceDTO;
import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.csvMapper.ResidentMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.service.interfaces.ResidentService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.comparator.ResidentCheckOutComparator;
import com.senla.hotel.utils.comparator.ResidentFullNameComparator;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private ResidentDtoMapper residentDtoMapper;
    @PropertyLoad(propertyName = "residents")
    private String property;

    @Override
    public ResidentDTO findById(final Long id) throws EntityNotFoundException, PersistException, EntityIsEmptyException {
        final Resident resident = residentDao.findById(id);
        if (resident == null) {
            throw new EntityNotFoundException(String.format("No resident with id %d%n", id));
        }
        return residentDtoMapper.destinationToSource(resident);
    }

    @Override
    public void addAttendanceToResident(final ResidentDTO resident, final AttendanceDTO attendance)
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
    public void createResident(final ResidentDTO resident) throws PersistException, EntityIsEmptyException {
        Resident entity = residentDtoMapper.sourceToDestination(resident);
        residentDao.create(entity);
    }

    @Override
    public List<ResidentDTO> showResidents(final SortField sortField) throws PersistException, EntityIsEmptyException {
        final List<Resident> residents = residentDao.getAll();
        final List<ResidentDTO> residentsDto = new ArrayList<>();
        for (Resident resident : residents) {
            ResidentDTO residentDTO = residentDtoMapper.destinationToSource(resident);
            residentsDto.add(residentDTO);
        }
        switch (sortField) {
            case NAME:
                return sortResidents(residentsDto, new ResidentFullNameComparator());
            case CHECK_OUT_DATE:
                return sortResidents(residentsDto, new ResidentCheckOutComparator());
            default:
                return residentsDto;
        }
    }

    private List<ResidentDTO> sortResidents(final List<ResidentDTO> residents, final Comparator<ResidentDTO> comparator) throws EntityIsEmptyException {
        final List<ResidentDTO> result = new ArrayList<>(residents);
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
//        csvWriter.write(property, ParseUtils.entitiesToCsv(residentMapper, showResidents(SortField.DEFAULT)));
    }

    @Override
    public int showCountResidents() throws PersistException {
        return residentDao.getAll().size();
    }
}
