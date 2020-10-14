package com.senla.hotel.controller;

import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.dto.CheckInDto;
import com.senla.hotel.dto.CheckOutDto;
import com.senla.hotel.dto.PriceDto;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.service.HotelAdminServiceImpl;
import com.senla.hotel.service.RoomHistoryServiceImpl;
import com.senla.hotel.service.interfaces.HotelAdminService;
import com.senla.hotel.service.interfaces.RoomHistoryService;
import com.senla.hotel.utils.HibernateUtil;
import mock.ResidentMock;
import mock.RoomMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HotelControllerTest extends AbstractControllerTest {

    @Mock
    private RoomDao roomDao;
    @Mock
    private ResidentDao residentDao;
    @Mock
    private RoomHistoryDao roomHistoryDao;
    @Mock
    private HibernateUtil hibernateUtil;
    @InjectMocks
    private HotelAdminService hotelAdminService = new HotelAdminServiceImpl();
    @Autowired
    private HibernateUtil hibernate;
    @InjectMocks
    private RoomHistoryService roomHistoryService = new RoomHistoryServiceImpl();
    @InjectMocks
    private HotelController hotelController = new HotelController(hotelAdminService, roomHistoryService);

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(hotelController)
                .alwaysDo(print())
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    void calculateBillTest() throws Exception {
        final Long residentId = 1L;
        final Long roomId = 1L;
        final Long historyId = 1L;
        final Resident resident = ResidentMock.getById(residentId);
        final Room room = RoomMock.getById(roomId);
        Set<RoomHistory> histories = new HashSet<>();
        final RoomHistory roomHistory =
                new RoomHistory(room, resident, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
                        HistoryStatus.CHECKED_IN);
        roomHistory.setId(historyId);
        histories.add(roomHistory);
        resident.setHistory(histories);
        given(residentDao.findById(residentId)).willReturn(resident);
        final BigDecimal price = BigDecimal.valueOf(2000.45);
        String jsonContent = mapper.writeValueAsString(new PriceDto(price));
        given(roomHistoryDao.calculateBill(historyId)).willReturn(price);

        mockMvc.perform(get("/admin/bill/" + historyId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));

        verify(residentDao, times(1)).findById(residentId);
        verify(roomHistoryDao, times(1)).calculateBill(historyId);
    }

    @Test
    void checkInTest() throws Exception {
        final Long residentId = 1L;
        final Long roomId = 1L;
        final Resident resident = ResidentMock.getById(residentId);
        final ResidentDto residentDto = ResidentMock.getDtoById(residentId);
        final Room room = RoomMock.getById(roomId);
        final RoomDto roomDto = RoomMock.getDtoById(roomId);
        roomDto.setId(roomId);
        residentDto.setId(residentId);
        final RoomHistory roomHistory =
                new RoomHistory(room, resident, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
                        HistoryStatus.CHECKED_IN);

        given(hibernateUtil.openSession()).willReturn(hibernate.openSession());
        given(residentDao.findById(residentId)).willReturn(resident);
        given(roomDao.findById(roomId)).willReturn(room);
        given(roomDao.update(room)).willReturn(room);
        given(roomHistoryDao.create(roomHistory)).willReturn(roomHistory);


        String content = mapper.writeValueAsString(new CheckInDto(residentDto, roomDto, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5)));
        mockMvc.perform(post("/admin/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated());

        verify(residentDao, times(1)).findById(residentId);
        verify(roomDao, times(1)).findById(roomId);
        verify(roomDao, times(1)).update(room);
        verify(roomHistoryDao, times(1)).create(roomHistory);
    }

    @Test
    void checkOutTest() throws Exception {
        final Long residentId = 1L;
        final Long roomId = 1L;
        final Resident resident = ResidentMock.getById(residentId);
        final ResidentDto residentDto = ResidentMock.getDtoById(residentId);
        final Room room = RoomMock.getById(roomId);
        residentDto.setId(residentId);

        final RoomHistory roomHistory =
                new RoomHistory(room, resident, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
                        HistoryStatus.CHECKED_IN);
        Set<RoomHistory> histories = new HashSet<>();
        histories.add(roomHistory);
        resident.setHistory(histories);
        given(hibernateUtil.openSession()).willReturn(hibernate.openSession());
        given(residentDao.findById(residentId)).willReturn(resident);
        given(roomHistoryDao.update(roomHistory)).willReturn(roomHistory);

        String content = mapper.writeValueAsString(new CheckOutDto(residentDto, LocalDate.of(2020, 8, 5)));
        mockMvc.perform(put("/admin/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk());

        verify(residentDao, times(1)).findById(residentId);
        verify(roomHistoryDao, times(1)).update(roomHistory);
    }
}
