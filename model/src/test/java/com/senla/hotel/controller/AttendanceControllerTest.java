package com.senla.hotel.controller;

import com.senla.hotel.config.application.SortFieldEnumConverter.SortFieldEnumConverter;
import com.senla.hotel.config.security.SpringSecurityConfig;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.dto.PriceDto;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.mapper.AttendanceDtoMapperImpl;
import com.senla.hotel.mapper.interfaces.dtoMapper.AttendanceDtoMapper;
import com.senla.hotel.service.AttendanceServiceImpl;
import com.senla.hotel.service.interfaces.AttendanceService;
import mock.AttendanceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AttendanceControllerTest extends AbstractControllerTest {

    @Mock
    private AttendanceDao attendanceDao;
    @Spy
    private AttendanceDtoMapper attendanceDtoMapper = new AttendanceDtoMapperImpl();
    @InjectMocks
    private AttendanceService attendanceService = new AttendanceServiceImpl();
    @InjectMocks
    private AttendanceController attendanceController = new AttendanceController(attendanceService);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FormattingConversionService formattingConversionService = new FormattingConversionService();
        formattingConversionService.addConverter(new SortFieldEnumConverter());

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(attendanceController)
                .setConversionService(formattingConversionService)
                .alwaysDo(print())
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    void createResidentTest() throws Exception {
        final Long attendanceId = 1L;

        Attendance attendance = AttendanceMock.getById(attendanceId);
        AttendanceDto expected = AttendanceMock.getDtoById(attendanceId);

        given(attendanceDao.create(attendance)).willReturn(attendance);

        mockMvc.perform(post("/attendances/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expected)))
                .andExpect(status().isCreated());

        verify(attendanceDao, times(1)).create(any(Attendance.class));
    }

    @Test
    public void testShowAttendances() throws Exception {
        List<Attendance> all = AttendanceMock.getAll();
        given(attendanceDao.getAllSortedBy(SortField.DEFAULT))
                .willReturn(all);
        String jsonContent = mapper.writeValueAsString(AttendanceMock.getAllDto());

        mockMvc.perform(get("/attendances/sort/ID")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }

    @Test
    void updateAttendancePriceTest() throws Exception {
        final Long attendanceId = 1L;
        Attendance attendance = AttendanceMock.getById(attendanceId);
        AttendanceDto expected = AttendanceMock.getDtoById(attendanceId);
        expected.setPrice(BigDecimal.TEN);

        given(attendanceDao.findById(attendanceId)).willReturn(attendance);
        given(attendanceDao.update(attendance)).willReturn(attendance);

        String content = mapper.writeValueAsString(new PriceDto(BigDecimal.valueOf(20.0)));
        mockMvc.perform(patch("/attendances/" + attendanceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk());

        verify(attendanceDao, times(1)).findById(attendanceId);
        verify(attendanceDao).update(any(Attendance.class));
    }

    @Test
    void shouldBeDeleteTest() throws Exception {
        final Long attendanceId = 1L;
        Attendance attendance = AttendanceMock.getById(attendanceId);

        given(attendanceDao.findById(attendanceId)).willReturn(attendance);

        mockMvc.perform(delete("/attendances/" + attendanceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(attendanceDao, times(1)).findById(attendanceId);
        verify(attendanceDao, times(1)).delete(attendance);
    }

    @Test
    void shouldFindByIdTest() throws Exception {
        final Long attendanceId = 1L;
        Attendance attendance = AttendanceMock.getById(attendanceId);
        AttendanceDto expected = AttendanceMock.getDtoById(attendanceId);

        given(attendanceDao.findById(attendanceId)).willReturn(attendance);

        mockMvc.perform(get("/attendances/" + attendanceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));

        verify(attendanceDao, times(1)).findById(attendanceId);
    }

    @Test
    void shouldUpdateAttendanceTest() throws Exception {
        final Long attendanceId = 1L;
        Attendance attendance = AttendanceMock.getById(attendanceId);
        AttendanceDto expected = AttendanceMock.getDtoById(attendanceId);

        mockMvc.perform(put("/attendances/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expected)))
                .andExpect(status().isOk());

        verify(attendanceDao, times(1)).update(attendance);
    }
}
