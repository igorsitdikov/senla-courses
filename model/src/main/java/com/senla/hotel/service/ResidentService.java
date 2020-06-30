package com.senla.hotel.service;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.repository.AttendanceRepository;
import com.senla.hotel.repository.ResidentRepository;
import com.senla.hotel.repository.RoomHistoryRepository;
import com.senla.hotel.repository.interfaces.IAttendanceRepository;
import com.senla.hotel.repository.interfaces.IResidentRepository;
import com.senla.hotel.repository.interfaces.IRoomHistoryRepository;
import com.senla.hotel.service.interfaces.IAttendanceService;
import com.senla.hotel.service.interfaces.IResidentService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.comparator.ResidentCheckOutComparator;
import com.senla.hotel.utils.comparator.ResidentFullNameComparator;
import com.senla.hotel.utils.csv.reader.CsvReader;
import com.senla.hotel.utils.csv.writer.CsvWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResidentService implements IResidentService {
    private static final String PROPERTY = "residents";
    private static ResidentService residentService;
    private final IResidentRepository residentRepository = ResidentRepository.getInstance();
    private final IRoomHistoryRepository roomHistoryRepository = RoomHistoryRepository.getInstance();
    private final IAttendanceService attendanceService = AttendanceService.getInstance();

    private ResidentService() {
    }

    public static ResidentService getInstance() {
        if (residentService == null) {
            residentService = new ResidentService();
        }
        return residentService;
    }

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
        final RoomHistory history = (RoomHistory) RoomHistoryRepository.getInstance()
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
        final Long attendanceId = attendance.getId();
        addAttendanceToResident(residentId, attendanceId);
    }

    @Override
    public void addAttendanceToResident(final Long residentId, final Long attendanceId) throws EntityNotFoundException {
        final Resident resident = findById(residentId);
        final Attendance attendance = attendanceService.findById(attendanceId);
        final List<Attendance> attendances = new ArrayList<>(resident.getHistory().getAttendances());
        final RoomHistory history = (RoomHistory) RoomHistoryRepository.getInstance()
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
        final List<Resident>
                residents = ParseUtils.stringToResidents(CsvReader.getInstance().read(PROPERTY));
        residentRepository.setResidents(residents);
    }

    @Override
    public void exportResidents() {
        CsvWriter.getInstance().write(PROPERTY, ParseUtils.residentsToCsv());
    }

    @Override
    public int showCountResidents() {
        return residentRepository.showTotalNumber();
    }

}
