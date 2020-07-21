package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.mapper.ResidentMapperImpl;
import com.senla.hotel.mapper.interfaces.EntityMapper;
import com.senla.hotel.repository.interfaces.ResidentRepository;
import com.senla.hotel.repository.interfaces.RoomHistoryRepository;
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
    private ResidentRepository residentRepository;
    @Autowired
    private RoomHistoryRepository roomHistoryRepository;
    @Autowired
    private AttendanceService attendanceService;
    @PropertyLoad(propertyName = "residents")
    private String property;

    @Override
    public List<Resident> sortResidents(final List<Resident> residents, final Comparator<Resident> comparator) {
        final List<Resident> result = new ArrayList<>(residents);
        result.sort(comparator);
        return result;
    }

    @Override
    public Resident findById(final Long id) throws EntityNotFoundException {
        final Resident resident = (Resident) residentRepository.findById(id);
        if (resident == null) {
            throw new EntityNotFoundException(String.format("No resident with id %d%n", id));
        }
        return resident;
    }

    @Override
    public void addHistoryToResident(final Long id, final RoomHistory roomHistory) throws EntityNotFoundException {
        final Resident resident = findById(id);
        residentRepository.addHistory(resident, roomHistory);
    }

    @Override
    public void addAttendanceToResident(final Long id, final Attendance attendance) throws EntityNotFoundException {
        final Resident resident = findById(id);
        final RoomHistory history = (RoomHistory) roomHistoryRepository
            .findById(resident.getHistory().getId());
        final List<Attendance> attendances = resident.getHistory().getAttendances();
        attendanceService.add(attendances, attendance);
        history.setAttendances(attendances);
        resident.getHistory().setAttendances(attendances);
    }

    @Override
    public void addAttendanceToResident(final Resident resident, final Attendance attendance)
        throws EntityNotFoundException {
        final Long residentId = resident.getId();
        final Long attendanceId = attendanceService.findByName(attendance.getName()).getId();
        addAttendanceToResident(residentId, attendanceId);
    }

    @Override
    public void addAttendanceToResident(final Long residentId, final Long attendanceId) throws EntityNotFoundException {
        final Resident resident = findById(residentId);
        final Attendance attendance = attendanceService.findById(attendanceId);
        final List<Attendance> attendances = new ArrayList<>(resident.getHistory().getAttendances());
        final RoomHistory history = (RoomHistory) roomHistoryRepository
            .findById(residentId);
        attendanceService.add(attendances, attendance);
        resident.getHistory().setAttendances(attendances);
        roomHistoryRepository.addAttendance(history.getId(), attendance);
    }

    @Override
    public void createResident(final Resident resident) {
        residentRepository.add(resident);
    }

    @Override
    public List<Resident> showResidents() {
        return residentRepository.getResidents();
    }

    @Override
    public List<Resident> showResidentsSortedByName() {
        final List<Resident> residents = showResidents();
        return sortResidents(residents, new ResidentFullNameComparator());
    }

    @Override
    public List<Resident> showResidentsSortedByCheckOutDate() {
        final List<Resident> residents = showResidents();
        return sortResidents(residents, new ResidentCheckOutComparator());
    }

    @Override
    public void importResidents() {
        EntityMapper<Resident> residentMapper = new ResidentMapperImpl();
        final List<Resident> residents =
            ParseUtils.stringToEntities(csvReader.read(property), residentMapper, Resident.class);
        residentRepository.setResidents(residents);
    }

    @Override
    public void exportResidents() {
        EntityMapper<Resident> residentMapper = new ResidentMapperImpl();
        csvWriter.write(property, ParseUtils.entitiesToCsv(residentMapper, showResidents()));
    }

    @Override
    public int showCountResidents() {
        return residentRepository.showTotalNumber();
    }

}
