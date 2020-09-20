package com.senla.hotel.service;

import com.senla.hotel.HotelTest;
import com.senla.hotel.dao.hibernate.AttendanceDaoImpl;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
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
public class AttendanceServiceImplTest {

    @Mock
    private AttendanceDaoImpl attendanceDao;
    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @Test
    void createResidentTest() throws PersistException {
        Attendance ironing = new Attendance(BigDecimal.valueOf(2.3).setScale(2), "Ironing");
        given(attendanceDao.create(ironing)).willReturn(ironing);

        attendanceService.createAttendance(ironing);

        verify(attendanceDao, times(1)).create(any(Attendance.class));
    }

    @Test
    void shouldReturnFindAll() throws PersistException {
        List<Attendance> attendances = new ArrayList<>();
        Attendance ironing = new Attendance(BigDecimal.valueOf(2.3).setScale(2), "Ironing");
        Attendance wakeup = new Attendance(BigDecimal.valueOf(1.5).setScale(2), "Wake-up");
        Attendance laundry = new Attendance(BigDecimal.valueOf(4.5).setScale(2), "Laundry");
        Attendance dogWalking = new Attendance(BigDecimal.valueOf(3.1).setScale(2), "Dog walking");
        attendances.add(ironing);
        attendances.add(wakeup);
        attendances.add(laundry);
        attendances.add(dogWalking);

        given(attendanceDao.getAllSortedBy(SortField.DEFAULT)).willReturn(attendances);
        List<Attendance> expected = attendanceService.showAttendances(SortField.DEFAULT);
        assertEquals("find all result: ", expected, attendances);
    }


    @Test
    void findAttendanceByIdTest() throws PersistException {
        final Long attendanceId = 1L;
        Attendance ironing = new Attendance(BigDecimal.valueOf(2.3).setScale(2), "Ironing");
        ironing.setId(attendanceId);
        given(attendanceDao.findById(attendanceId)).willReturn(ironing);
        Attendance actual = attendanceService.findById(attendanceId);

        verify(attendanceDao, times(1)).findById(attendanceId);
        assertEquals("find by id result: ", ironing, actual);
    }

    @Test
    void updateAttendancePriceTest() throws PersistException {
        final Long attendanceId = 1L;
        Attendance ironing = new Attendance(BigDecimal.valueOf(2.3).setScale(2), "Ironing");
        ironing.setId(attendanceId);
        given(attendanceDao.findById(attendanceId)).willReturn(ironing);
        given(attendanceDao.update(ironing)).willReturn(ironing);

        final Attendance actual = attendanceService.changeAttendancePrice(attendanceId, BigDecimal.TEN);
        assertEquals("updated price: ", ironing, actual);

        verify(attendanceDao, times(1)).findById(attendanceId);
        verify(attendanceDao).update(any(Attendance.class));
    }

    @Test
    void shouldBeDeleteTest() throws PersistException {
        final Long attendanceId = 1L;

        Attendance ironing = new Attendance(BigDecimal.valueOf(2.3).setScale(2), "Ironing");
        ironing.setId(attendanceId);
        given(attendanceDao.findById(attendanceId)).willReturn(ironing);

        attendanceService.delete(attendanceId);

        verify(attendanceDao, times(1)).findById(attendanceId);
        verify(attendanceDao, times(1)).delete(ironing);
    }
}
