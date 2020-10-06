package com.senla.hotel.service;

import com.senla.hotel.HotelTest;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.EntityAlreadyExistsException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.AttendanceDtoMapperImpl;
import com.senla.hotel.mapper.interfaces.dtoMapper.AttendanceDtoMapper;
import com.senla.hotel.service.interfaces.AttendanceService;
import mock.AttendanceMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
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
public class AttendanceServiceImplTest {

    @Mock
    private AttendanceDao attendanceDao;
    @Spy
    private AttendanceDtoMapper attendanceDtoMapper = new AttendanceDtoMapperImpl();
    @InjectMocks
    private AttendanceService attendanceService = new AttendanceServiceImpl();

    @Test
    void createResidentTest() throws PersistException, EntityAlreadyExistsException {
        final Long attendanceId = 1L;

        Attendance attendance = AttendanceMock.getById(attendanceId);
        AttendanceDto expected = AttendanceMock.getDtoById(attendanceId);

        given(attendanceDao.create(attendance)).willReturn(attendance);

        attendanceService.createAttendance(expected);

        verify(attendanceDao, times(1)).create(any(Attendance.class));
    }

    @Test
    void shouldReturnFindAll() throws PersistException {
        List<AttendanceDto> actual = AttendanceMock.getAllDto();

        List<Attendance> all = AttendanceMock.getAll();
        given(attendanceDao.getAllSortedBy(SortField.DEFAULT))
                .willReturn(all);
        List<AttendanceDto> expected = attendanceService.showAttendances(SortField.DEFAULT);
        List<String> actualNames = actual
                .stream()
                .map(AttendanceDto::getName)
                .collect(Collectors.toList());
        List<String> expectedNames = expected
                .stream()
                .map(AttendanceDto::getName)
                .collect(Collectors.toList());
        assertEquals("", expected.size(), actual.size());
        assertEquals("", actualNames, expectedNames);
    }


    @Test
    void findAttendanceByIdTest() throws PersistException, EntityNotFoundException {
        final Long attendanceId = 1L;

        Attendance attendance = AttendanceMock.getById(attendanceId);
        AttendanceDto expected = AttendanceMock.getDtoById(attendanceId);
        given(attendanceDao.findById(attendanceId)).willReturn(attendance);
        AttendanceDto actual = attendanceService.findById(attendanceId);

        verify(attendanceDao, times(1)).findById(attendanceId);
        assertEquals("find by id result: ", expected, actual);
    }

    @Test
    void updateAttendancePriceTest() throws PersistException, EntityNotFoundException {
        final Long attendanceId = 1L;
        Attendance attendance = AttendanceMock.getById(attendanceId);
        AttendanceDto expected = AttendanceMock.getDtoById(attendanceId);
        expected.setPrice(BigDecimal.TEN);

        given(attendanceDao.findById(attendanceId)).willReturn(attendance);
        given(attendanceDao.update(attendance)).willReturn(attendance);

        final AttendanceDto actual = attendanceService.changeAttendancePrice(attendanceId, BigDecimal.TEN);
        assertEquals("updated price: ", expected, actual);

        verify(attendanceDao, times(1)).findById(attendanceId);
        verify(attendanceDao).update(any(Attendance.class));
    }

    @Test
    void shouldBeDeleteTest() throws PersistException, EntityNotFoundException {
        final Long attendanceId = 1L;
        Attendance attendance = AttendanceMock.getById(attendanceId);

        given(attendanceDao.findById(attendanceId)).willReturn(attendance);

        attendanceService.delete(attendanceId);

        verify(attendanceDao, times(1)).findById(attendanceId);
        verify(attendanceDao, times(1)).delete(attendance);
    }
}
