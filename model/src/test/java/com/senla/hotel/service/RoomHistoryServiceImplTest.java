package com.senla.hotel.service;

import com.senla.hotel.HotelTest;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.dto.RoomHistoryDto;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.Gender;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.AttendanceDtoMapperImpl;
import com.senla.hotel.mapper.ResidentDtoMapperImpl;
import com.senla.hotel.mapper.RoomDtoMapperImpl;
import com.senla.hotel.mapper.RoomHistoryDtoMapperImpl;
import com.senla.hotel.mapper.interfaces.dtoMapper.AttendanceDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomHistoryDtoMapper;
import com.senla.hotel.service.interfaces.RoomHistoryService;
import mock.ResidentMock;
import mock.RoomMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = HotelTest.class)
public class RoomHistoryServiceImplTest {

    @Mock
    private RoomHistoryDao roomHistoryDao;
    @Spy
    private RoomDtoMapper roomDtoMapper = new RoomDtoMapperImpl();
    @Spy
    private ResidentDtoMapper residentDtoMapper = new ResidentDtoMapperImpl();
    @Spy
    private RoomHistoryDtoMapper roomHistoryDtoMapper = new RoomHistoryDtoMapperImpl(residentDtoMapper, roomDtoMapper);
    @InjectMocks
    private RoomHistoryService roomHistoryService = new RoomHistoryServiceImpl();

    @Test
    void findRoomHistoryByIdTest() throws PersistException, EntityNotFoundException {
        final Long roomHistoryId = 1L;
        final Long roomId = 1L;
        final Long residentId = 1L;
        final RoomDto roomDto = RoomMock.getDtoById(roomId);
        final Room room = RoomMock.getById(roomId);
        final ResidentDto residentDto = ResidentMock.getDtoById(residentId);
        residentDto.setHistoryDtos(new HashSet<>());
        final Resident resident = ResidentMock.getById(residentId);

        final RoomHistory roomHistory =
                new RoomHistory(room, resident, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
                        HistoryStatus.CHECKED_IN);
        final RoomHistoryDto expected =
                new RoomHistoryDto(null, roomDto, residentDto, new ArrayList<>(), LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
                        HistoryStatus.CHECKED_IN);
        given(roomHistoryDao.findById(roomHistoryId)).willReturn(roomHistory);
        RoomHistoryDto actual = roomHistoryService.findById(roomHistoryId);

        assertEquals("find by id result: ", expected, actual);
        verify(roomHistoryDao, times(1)).findById(roomHistoryId);
    }

    @Test
    void shouldReturnFindAllTest() throws PersistException {
        List<RoomHistory> roomHistories = new ArrayList<>();
        List<RoomHistoryDto> expected = new ArrayList<>();
        final Long roomHistoryId = 1L;
        final Long roomId = 1L;
        final Long residentId = 1L;
        final RoomDto roomDto = RoomMock.getDtoById(roomId);
        final Room room = RoomMock.getById(roomId);
        final ResidentDto residentDto = ResidentMock.getDtoById(residentId);
        residentDto.setHistoryDtos(new HashSet<>());
        final Resident resident = ResidentMock.getById(residentId);

        final RoomHistory roomHistory1 =
                new RoomHistory(room, resident, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
                        HistoryStatus.CHECKED_OUT);
        final RoomHistory roomHistory2 =
                new RoomHistory(room, resident, LocalDate.of(2020, 9, 3), LocalDate.of(2020, 9, 5),
                        HistoryStatus.CHECKED_IN);
        final RoomHistoryDto expected1 =
                new RoomHistoryDto(null, roomDto, residentDto, new ArrayList<>(), LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
                        HistoryStatus.CHECKED_OUT);
        final RoomHistoryDto expected2 =
                new RoomHistoryDto(null, roomDto, residentDto, new ArrayList<>(), LocalDate.of(2020, 9, 3), LocalDate.of(2020, 9, 5),
                        HistoryStatus.CHECKED_IN);
        expected.add(expected1);
        expected.add(expected2);
        roomHistories.add(roomHistory1);
        roomHistories.add(roomHistory2);

        given(roomHistoryDao.getAll()).willReturn(roomHistories);
        List<RoomHistoryDto> actual = roomHistoryService.showHistories();
        assertEquals("find all result: ", expected.size(), 2);
        assertEquals("find all result: ", expected.size(), actual.size());
        assertEquals("find all result: ", expected.get(0), actual.get(0));
        assertEquals("find all result: ", expected.get(1), actual.get(1));

        verify(roomHistoryDao, times(1)).getAll();
    }

    @Test
    void createRoomHistoryTest() throws PersistException {
        final Long roomId = 1L;
        final Long residentId = 1L;
        final RoomDto roomDto = RoomMock.getDtoById(roomId);
        final Room room = RoomMock.getById(roomId);
        final ResidentDto residentDto = ResidentMock.getDtoById(residentId);
        residentDto.setHistoryDtos(new HashSet<>());
        final Resident resident = ResidentMock.getById(residentId);

        final RoomHistory roomHistory =
                new RoomHistory(room, resident, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
                        HistoryStatus.CHECKED_OUT);
        final RoomHistoryDto expected =
                new RoomHistoryDto(null, roomDto,
                        residentDto, new ArrayList<>(),
                        LocalDate.of(2020, 8, 3),
                        LocalDate.of(2020, 8, 5),
                        HistoryStatus.CHECKED_OUT);

        given(roomHistoryDao.create(any(RoomHistory.class))).willReturn(roomHistory);

        final RoomHistoryDto actual = roomHistoryService.create(expected);
        assertEquals("find all result: ", expected, actual);
        verify(roomHistoryDao, times(1)).create(any(RoomHistory.class));
    }
}
