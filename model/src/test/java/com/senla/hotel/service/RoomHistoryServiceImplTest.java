//package com.senla.hotel.service;
//
//import com.senla.hotel.HotelTest;
//import com.senla.hotel.dao.interfaces.RoomHistoryDao;
//import com.senla.hotel.dto.ResidentDto;
//import com.senla.hotel.dto.RoomDto;
//import com.senla.hotel.dto.RoomHistoryDto;
//import com.senla.hotel.entity.Resident;
//import com.senla.hotel.entity.Room;
//import com.senla.hotel.entity.RoomHistory;
//import com.senla.hotel.enumerated.Accommodation;
//import com.senla.hotel.enumerated.Gender;
//import com.senla.hotel.enumerated.HistoryStatus;
//import com.senla.hotel.enumerated.RoomStatus;
//import com.senla.hotel.enumerated.Stars;
//import com.senla.hotel.exceptions.EntityNotFoundException;
//import com.senla.hotel.exceptions.PersistException;
//import com.senla.hotel.mapper.AttendanceDtoMapperImpl;
//import com.senla.hotel.mapper.ResidentDtoMapperImpl;
//import com.senla.hotel.mapper.RoomDtoMapperImpl;
//import com.senla.hotel.mapper.RoomHistoryDtoMapperImpl;
//import com.senla.hotel.mapper.interfaces.dtoMapper.AttendanceDtoMapper;
//import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
//import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
//import com.senla.hotel.mapper.interfaces.dtoMapper.RoomHistoryDtoMapper;
//import com.senla.hotel.service.interfaces.RoomHistoryService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.util.AssertionErrors.assertEquals;
//
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = HotelTest.class)
//public class RoomHistoryServiceImplTest {
//
//    @Mock
//    private RoomHistoryDao roomHistoryDao;
//    @Spy
//    private RoomHistoryDtoMapper roomHistoryDtoMapper = new RoomHistoryDtoMapperImpl();
//    @Spy
//    private ResidentDtoMapper residentDtoMapper = new ResidentDtoMapperImpl();
//    @Spy
//    private AttendanceDtoMapper attendanceDtoMapper = new AttendanceDtoMapperImpl();
//    @Spy
//    private RoomDtoMapper roomDtoMapper = new RoomDtoMapperImpl();
//    @InjectMocks
//    private RoomHistoryService roomHistoryService = new RoomHistoryServiceImpl();
//
//    @Test
//    void findRoomHistoryByIdTest() throws PersistException, EntityNotFoundException {
//        final Long roomHistoryId = 1L;
//        final Room room101 =
//            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);
//        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
//
//        final RoomHistory roomHistory =
//            new RoomHistory(room101, mike, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
//                            HistoryStatus.CHECKED_IN);
//        given(roomHistoryDao.findById(roomHistoryId)).willReturn(roomHistory);
//        RoomHistoryDto actual = roomHistoryService.findById(roomHistoryId);
//
//        assertEquals("find by id result: ", roomHistory, actual);
//        verify(roomHistoryDao, times(1)).findById(roomHistoryId);
//    }
//
//    @Test
//    void shouldReturnFindAllTest() throws PersistException {
//        List<RoomHistory> roomHistories = new ArrayList<>();
//        final Room room101 =
//            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);
//        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
//
//        final RoomHistory roomHistory1 =
//            new RoomHistory(room101, mike, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
//                            HistoryStatus.CHECKED_OUT);
//        final RoomHistory roomHistory2 =
//            new RoomHistory(room101, mike, LocalDate.of(2020, 9, 3), LocalDate.of(2020, 9, 5),
//                            HistoryStatus.CHECKED_IN);
//
//        roomHistories.add(roomHistory1);
//        roomHistories.add(roomHistory2);
//
//        given(roomHistoryDao.getAll()).willReturn(roomHistories);
//        List<RoomHistoryDto> expected = roomHistoryService.showHistories();
//        assertEquals("find all result: ", expected, roomHistories);
//
//        verify(roomHistoryDao, times(1)).getAll();
//    }
//
//
//    @Test
//    void createRoomHistoryTest() throws PersistException {
//        final RoomDto room101 = new RoomDto();
//        room101.setId(1L);
//        room101.setNumber(101);
//        room101.setStars(Stars.STANDARD);
//        room101.setAccommodation(Accommodation.SGL);
//        room101.setPrice(BigDecimal.valueOf(100));
//        room101.setStatus(RoomStatus.VACANT);
//        final ResidentDto mike = new ResidentDto("Mike", "Smith", Gender.MALE, false, "1234567", null);
//
//        final RoomHistoryDto roomHistory =
//            new RoomHistoryDto(room101, mike, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
//                               HistoryStatus.CHECKED_IN);
//
//        given(roomHistoryService.create(roomHistory)).willReturn(roomHistory);
//
//        roomHistoryService.create(roomHistory);
//
//        verify(roomHistoryDao, times(1)).create(any(RoomHistory.class));
//    }
//}
