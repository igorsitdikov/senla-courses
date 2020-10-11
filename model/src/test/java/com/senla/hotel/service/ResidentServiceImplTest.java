package com.senla.hotel.service;

import com.senla.hotel.HotelTest;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.AttendanceDtoMapperImpl;
import com.senla.hotel.mapper.ResidentDtoMapperImpl;
import com.senla.hotel.mapper.RoomDtoMapperImpl;
import com.senla.hotel.mapper.interfaces.dtoMapper.AttendanceDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.service.interfaces.ResidentService;
import mock.AttendanceMock;
import mock.ResidentMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = HotelTest.class)
public class ResidentServiceImplTest {
    @Mock
    private AttendanceDao attendanceDao;
    @InjectMocks
    private ResidentService residentService = new ResidentServiceImpl();
    @Spy
    private RoomDtoMapper roomDtoMapper = new RoomDtoMapperImpl();
    @Spy
    private ResidentDtoMapper residentDtoMapper = new ResidentDtoMapperImpl();
    @Spy
    private AttendanceDtoMapper attendanceDtoMapper = new AttendanceDtoMapperImpl();
    @Mock
    private ResidentDao residentDao;
    @Mock
    private RoomHistoryDao roomHistoryDao;

    @Test
    void findResidentByIdTest() throws PersistException, EntityNotFoundException {
        final Long residentId = 1L;
        final Resident resident = ResidentMock.getById(residentId);
        final ResidentDto expected = ResidentMock.getDtoById(residentId);
        expected.setHistoryDtos(new HashSet<>());
        given(residentDao.findById(residentId)).willReturn(resident);
        ResidentDto actual = residentService.findById(residentId);

        assertEquals("find by id result: ", expected, actual);
        verify(residentDao, times(1)).findById(residentId);
    }

    @Test
    void createResidentTest() throws PersistException {
        final Long residentId = 1L;
        final ResidentDto expected = ResidentMock.getDtoById(residentId);

        residentService.createResident(expected);

        verify(residentDao, times(1)).create(any(Resident.class));
    }

    @Test
    void addAttendanceToResidentTest() throws PersistException, EntityNotFoundException {
        final Long residentId = 1L;
        final Long attendanceId = 3L;

        final Resident resident = ResidentMock.getById(residentId);
        resident.setId(residentId);
        final ResidentDto residentDto = ResidentMock.getDtoById(residentId);
        Attendance attendance = AttendanceMock.getById(attendanceId);
        attendance.setId(attendanceId);
        AttendanceDto attendanceDto = AttendanceMock.getDtoById(attendanceId);

        final RoomHistory roomHistory =
            new RoomHistory(new Room(), resident, LocalDate.now(), LocalDate.now(), HistoryStatus.CHECKED_IN);

        given(roomHistoryDao.getByResidentAndCheckedInStatus(residentId)).willReturn(roomHistory);
        given(attendanceDao.findById(attendanceId)).willReturn(attendance);

        residentService.addAttendanceToResident(residentDto, attendanceDto);

        verify(attendanceDao, times(1)).findById(attendanceId);
        verify(roomHistoryDao, times(1)).getByResidentAndCheckedInStatus(residentId);
    }

    @Test
    void shouldReturnFindAllTest() throws PersistException {
        List<Resident> residents = ResidentMock.getAll();
        List<ResidentDto> expected = ResidentMock.getAllDto();

        given(residentDao.getAllSortedBy(SortField.DEFAULT)).willReturn(residents);
        List<ResidentDto> actual = residentService.showResidents(SortField.DEFAULT);
        List<String> actualNames = actual
                .stream()
                .map(ResidentDto::getFirstName)
                .collect(Collectors.toList());
        List<String> expectedNames = expected
                .stream()
                .map(ResidentDto::getFirstName)
                .collect(Collectors.toList());
        assertEquals("find all result: ", expectedNames, actualNames);

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
