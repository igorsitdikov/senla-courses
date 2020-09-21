package com.senla.hotel.service;

import com.senla.hotel.HotelTest;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.Gender;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.ResidentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = HotelTest.class)
public class ResidentServiceImplTest {
    @Mock
    private AttendanceDao attendanceDao;
    @InjectMocks
    private ResidentService residentService = new ResidentServiceImpl();
    @Mock
    private ResidentDao residentDao;
    @Mock
    private RoomHistoryDao roomHistoryDao;

    @Test
    void findResidentByIdTest() throws PersistException, EntityNotFoundException {
        final Long residentId = 1L;
        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
        mike.setId(residentId);

        given(residentDao.findById(residentId)).willReturn(mike);
        Resident actual = residentService.findById(residentId);

        assertEquals("find by id result: ", mike, actual);
        verify(residentDao, times(1)).findById(residentId);
    }

    @Test
    void createResidentTest() throws PersistException {
        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
        given(residentDao.create(mike)).willReturn(mike);

        residentService.createResident(mike);

        verify(residentDao, times(1)).create(any(Resident.class));
    }

    @Test
    void addAttendanceToResidentTest() throws PersistException, EntityNotFoundException {
        final Long residentId = 1L;
        final Long attendanceId = 3L;
        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
        mike.setId(residentId);
        final Attendance ironing = new Attendance(BigDecimal.valueOf(2.3).setScale(2), "Ironing");
        ironing.setId(attendanceId);
        final RoomHistory roomHistory =
            new RoomHistory(new Room(), mike, LocalDate.now(), LocalDate.now(), HistoryStatus.CHECKED_IN);

        given(roomHistoryDao.getByResidentAndCheckedInStatus(residentId)).willReturn(roomHistory);
        given(attendanceDao.findById(attendanceId)).willReturn(ironing);

        residentService.addAttendanceToResident(mike, ironing);

        verify(attendanceDao, times(1)).findById(attendanceId);
        verify(roomHistoryDao, times(1)).getByResidentAndCheckedInStatus(residentId);
    }

    @Test
    void shouldReturnFindAllTest() throws PersistException {
        List<Resident> residents = new ArrayList<>();
        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
        final Resident alex = new Resident("Alex", "Smith", Gender.MALE, false, "1234569", null);
        final Resident juliet = new Resident("Juliet", "Fox", Gender.FEMALE, true, "1234568", null);
        final Resident stephani = new Resident("Stephani", "Brown", Gender.FEMALE, false, "1234560", null);
        final Resident carl = new Resident("Carl", "Patoshek", Gender.MALE, false, "1234561", null);

        residents.add(mike);
        residents.add(alex);
        residents.add(juliet);
        residents.add(stephani);
        residents.add(carl);

        given(residentDao.getAllSortedBy(SortField.DEFAULT)).willReturn(residents);
        List<Resident> expected = residentService.showResidents(SortField.DEFAULT);
        assertEquals("find all result: ", expected, residents);

        verify(residentDao, times(1)).getAllSortedBy(SortField.DEFAULT);
    }

    @Test
    void showCountResidentsTest() throws PersistException {
        Integer count = 3;
        given(residentDao.countTotalResidents()).willReturn(count);

        Integer actual = residentService.showCountResidents();

        assertEquals("count ", count, actual);
        verify(residentDao, times(1)).countTotalResidents();
    }

}
