package com.senla.hotel.controller;

import com.senla.hotel.config.application.SortFieldEnumConverter.SortFieldEnumConverter;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.dto.PriceDto;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.mapper.ResidentDtoMapperImpl;
import com.senla.hotel.mapper.RoomDtoMapperImpl;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.service.HotelAdminServiceImpl;
import com.senla.hotel.service.RoomServiceImpl;
import com.senla.hotel.service.interfaces.HotelAdminService;
import com.senla.hotel.service.interfaces.RoomService;
import mock.ResidentMock;
import mock.RoomMock;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoomControllerTest extends AbstractControllerTest {

    @Mock
    private RoomDao roomDao;
    @Mock
    private ResidentDao residentDao;
    @Mock
    private RoomHistoryDao roomHistoryDao;
    @Mock
    private AttendanceDao attendanceDao;
    @Spy
    private RoomDtoMapper roomDtoMapper = new RoomDtoMapperImpl();
    @InjectMocks
    private HotelAdminService hotelAdminService = new HotelAdminServiceImpl();
    @Spy
    private ResidentDtoMapper residentDtoMapper = new ResidentDtoMapperImpl();
    @InjectMocks
    private RoomService roomService = new RoomServiceImpl();
    @InjectMocks
    private RoomController roomController = new RoomController(roomService);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FormattingConversionService formattingConversionService = new FormattingConversionService();
        formattingConversionService.addConverter(new SortFieldEnumConverter()); //Here

        ReflectionTestUtils.setField(roomController, "statusAllow", true);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(roomController)
                .setConversionService(formattingConversionService)
                .alwaysDo(print())
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    void shouldReturnFindAllTest() throws Exception {
        List<Room> rooms = RoomMock.getAll();
        List<RoomDto> expected = RoomMock.getAllDto();

        final String jsonContent = mapper.writeValueAsString(expected);

        given(roomDao.getAllSortedBy(SortField.DEFAULT)).willReturn(rooms);
        mockMvc.perform(get("/rooms/all/sort/ID")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));

        verify(roomDao, times(1)).getAllSortedBy(SortField.DEFAULT);
    }

    @Test
    void shouldReturnFindVacantTest() throws Exception {
        List<Room> rooms = RoomMock.getAll()
                .stream()
                .filter(room -> room.getStatus() == RoomStatus.VACANT)
                .collect(Collectors.toList());
        List<RoomDto> expected = RoomMock.getAllDto()
                .stream()
                .filter(room -> room.getStatus() == RoomStatus.VACANT)
                .collect(Collectors.toList());

        final String jsonContent = mapper.writeValueAsString(expected);

        given(roomDao.getVacantRooms(SortField.DEFAULT)).willReturn(rooms);
        mockMvc.perform(get("/rooms/vacant/sort/ID")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));

        verify(roomDao, times(1)).getVacantRooms(SortField.DEFAULT);
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void showRoomDetailsTest() throws Exception {
        final Integer roomNumber = 101;
        final Long roomId = 1L;
        final Room room = RoomMock.getById(roomId);
        room.setHistories(new ArrayList<>());
        final RoomDto expected = RoomMock.getDtoById(roomId);
        final String jsonContent = mapper.writeValueAsString(expected);

        given(roomDao.findByNumber(roomNumber)).willReturn(room);
        mockMvc.perform(get("/rooms/" + roomNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));

        verify(roomDao, times(1)).findByNumber(roomNumber);
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void changePriceTest() throws Exception {
        final Integer roomNumber = 101;
        final Long roomId = 1L;
        final Room room = RoomMock.getById(roomId);
        given(roomDao.findByNumber(roomNumber)).willReturn(room);
        given(roomDao.update(room)).willReturn(room);

        String content = mapper.writeValueAsString(new PriceDto(BigDecimal.valueOf(45)));
        mockMvc.perform(patch("/rooms/price/" + roomNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk());

        verify(roomDao, times(1)).findByNumber(room.getNumber());
        verify(roomDao, times(1)).update(room);
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void changeStatusTest() throws Exception {
        final Integer roomNumber = 101;
        final Long roomId = 1L;
        final Room room = RoomMock.getById(roomId);
        room.setId(roomId);
        given(roomDao.findById(roomId)).willReturn(room);
        given(roomDao.findByNumber(roomNumber)).willReturn(room);
        given(roomDao.update(room)).willReturn(room);


        mockMvc.perform(patch("/rooms/status/" + roomNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .param("status", RoomStatus.VACANT.toString()))
                .andExpect(status().isOk());

        verify(roomDao, times(1)).findByNumber(roomNumber);
        verify(roomDao, times(1)).findById(roomId);
        verify(roomDao, times(1)).update(room);
    }


    @Test
    @WithMockUser(roles="ADMIN")
    void showLastResidentsTest() throws Exception {
        final Long roomId = 1L;
        final Integer limit = 3;
        final RoomDto room = RoomMock.getDtoById(roomId);
        room.setId(roomId);
        final List<Resident> residents = ResidentMock.getAll().stream().limit(limit).collect(Collectors.toList());
        final List<ResidentDto> expected = ResidentMock.getAllDto().stream().limit(limit).collect(Collectors.toList());

        given(residentDao.getLastResidentsByRoomId(roomId, limit)).willReturn(residents);
        String jsonContent = mapper.writeValueAsString(expected);
        mockMvc.perform(get("/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(roomId))
                .param("amount", String.valueOf(limit)))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));

        verify(residentDao, times(1)).getLastResidentsByRoomId(roomId, limit);
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void createRoomTest() throws Exception {
        final Long roomId = 1L;
        final Room room = RoomMock.getById(roomId);
        room.setHistories(null);
        final RoomDto expected = RoomMock.getDtoById(roomId);
        String content = mapper.writeValueAsString(expected);
        given(roomDao.create(room)).willReturn(room);
        mockMvc.perform(post("/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated());

        verify(roomDao, times(1)).create(any(Room.class));
    }

}
