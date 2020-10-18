package com.senla.hotel.controller;

import com.senla.hotel.config.application.SortFieldEnumConverter.SortFieldEnumConverter;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.dto.AmountDto;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.mapper.ResidentDtoMapperImpl;
import com.senla.hotel.mapper.RoomDtoMapperImpl;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.service.HotelAdminServiceImpl;
import com.senla.hotel.service.ResidentServiceImpl;
import com.senla.hotel.service.interfaces.HotelAdminService;
import com.senla.hotel.service.interfaces.ResidentService;
import mock.ResidentMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResidentControllerTest extends AbstractControllerTest {

    @Mock
    private RoomDao roomDao;
    @Mock
    private ResidentDao residentDao;
    @Mock
    private RoomHistoryDao roomHistoryDao;
    @Mock
    private AttendanceDao attendanceDao;
    @InjectMocks
    private HotelAdminService hotelAdminService = new HotelAdminServiceImpl();
    @Spy
    private RoomDtoMapper roomDtoMapper = new RoomDtoMapperImpl();
    @Spy
    private ResidentDtoMapper residentDtoMapper = new ResidentDtoMapperImpl();
    @InjectMocks
    private ResidentService residentService = new ResidentServiceImpl();
    @InjectMocks
    private ResidentController residentController = new ResidentController(residentService);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FormattingConversionService formattingConversionService = new FormattingConversionService();
        formattingConversionService.addConverter(new SortFieldEnumConverter());

        this.mockMvc = MockMvcBuilders.standaloneSetup(residentController)
                .setConversionService(formattingConversionService)
                .alwaysDo(print())
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void shouldReturnFindAllTest() throws Exception {
        List<Resident> residents = ResidentMock.getAll();
        List<ResidentDto> expected = ResidentMock.getAllDto();
        final String jsonContent = mapper.writeValueAsString(expected);
        given(residentDao.getAllSortedBy(SortField.DEFAULT)).willReturn(residents);
        mockMvc.perform(get("/residents/sort/ID")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(content().json(jsonContent));

        verify(residentDao, times(1)).getAllSortedBy(SortField.DEFAULT);
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void showCountResidentsTest() throws Exception {
        Integer amount = 3;
        given(residentDao.countTotalResidents()).willReturn(amount);
        String jsonContent = mapper.writeValueAsString(new AmountDto(amount));
        mockMvc.perform(get("/residents/amount")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(content().json(jsonContent));

        verify(residentDao, times(1)).countTotalResidents();
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void addAttendanceToResidentTest() throws Exception {
        final Long residentId = 1L;
        final Long attendanceId = 3L;

        final Resident resident = ResidentMock.getById(residentId);
        resident.setId(residentId);
        Attendance attendance = mock.AttendanceMock.getById(attendanceId);
        attendance.setId(attendanceId);

        final RoomHistory roomHistory =
                new RoomHistory(new Room(), resident, LocalDate.now(), LocalDate.now(), HistoryStatus.CHECKED_IN);

        given(roomHistoryDao.getByResidentAndCheckedInStatus(residentId)).willReturn(roomHistory);
        given(attendanceDao.findById(attendanceId)).willReturn(attendance);

        mockMvc.perform(get("/residents")
                .param("residentId", String.valueOf(residentId))
                .param("attendanceId", String.valueOf(attendanceId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(attendanceDao, times(1)).findById(attendanceId);
        verify(roomHistoryDao, times(1)).getByResidentAndCheckedInStatus(residentId);
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void createResidentTest() throws Exception {
        final Long residentId = 1L;
        final ResidentDto expected = ResidentMock.getDtoById(residentId);

        String jsonContent = mapper.writeValueAsString(expected);
        mockMvc.perform(post("/residents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isCreated());

        verify(residentDao, times(1)).create(any(Resident.class));
    }

}
