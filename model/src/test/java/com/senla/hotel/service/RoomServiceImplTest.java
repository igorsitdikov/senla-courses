package com.senla.hotel.service;

import com.senla.hotel.HotelTest;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.ResidentDtoMapperImpl;
import com.senla.hotel.mapper.RoomDtoMapperImpl;
import com.senla.hotel.mapper.interfaces.dtoMapper.ResidentDtoMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomDtoMapper;
import com.senla.hotel.service.interfaces.RoomService;
import mock.ResidentMock;
import mock.RoomMock;
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
import java.util.ArrayList;
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
public class RoomServiceImplTest {
    @Mock
    private RoomDao roomDao;
    @Spy
    private RoomDtoMapper roomDtoMapper = new RoomDtoMapperImpl();
    @Spy
    private ResidentDtoMapper residentDtoMapper = new ResidentDtoMapperImpl();
    @InjectMocks
    private RoomService roomService = new RoomServiceImpl();
    @Mock
    private ResidentDao residentDao;

    @Test
    void findRoomByIdTest() throws PersistException, EntityNotFoundException {
        final Long roomId = 1L;
        final Room room = RoomMock.getById(roomId);
        room.setHistories(new ArrayList<>());
        final RoomDto expected = RoomMock.getDtoById(roomId);

        given(roomDao.findById(roomId)).willReturn(room);
        RoomDto actual = roomService.findById(roomId);

        assertEquals("find by id result: ", expected, actual);
        verify(roomDao, times(1)).findById(roomId);
    }

    @Test
    void findRoomByNumberTest() throws PersistException, EntityNotFoundException {
        final Integer roomNumber = 101;
        final Long roomId = 1L;
        final Room room = RoomMock.getById(roomId);
        final RoomDto expected = RoomMock.getDtoById(roomId);

        given(roomDao.findByNumber(roomNumber)).willReturn(room);
        RoomDto actual = roomService.findByNumber(roomNumber); // showRoomDetails

        assertEquals("find by number result: ", expected, actual);
        verify(roomDao, times(1)).findByNumber(roomNumber);
    }

    @Test
    void shouldReturnFindAllTest() throws PersistException {
        List<Room> rooms = RoomMock.getAll();
        List<RoomDto> expected = RoomMock.getAllDto();

        given(roomDao.getAllSortedBy(SortField.DEFAULT)).willReturn(rooms);
        List<RoomDto> actual = roomService.showAll(SortField.DEFAULT);
        List<Integer> actualNames = actual
                .stream()
                .map(RoomDto::getNumber)
                .collect(Collectors.toList());
        List<Integer> expectedNames = expected
                .stream()
                .map(RoomDto::getNumber)
                .collect(Collectors.toList());
        assertEquals("find all result: ", expectedNames, actualNames);
        verify(roomDao, times(1)).getAllSortedBy(SortField.DEFAULT);
    }

    @Test
    void createRoomTest() throws PersistException {
        final Long roomId = 1L;
        final Room room = RoomMock.getById(roomId);
        room.setHistories(null);
        final RoomDto expected = RoomMock.getDtoById(roomId);

        given(roomDao.create(room)).willReturn(room);

        roomService.addRoom(expected);

        verify(roomDao, times(1)).create(any(Room.class));
    }

    @Test
    void showLastResidentsTest() throws PersistException, EntityNotFoundException {
        final Long roomId = 1L;
        final Integer limit = 3;
        final RoomDto room = RoomMock.getDtoById(roomId);
        room.setId(roomId);
        final List<Resident> residents = ResidentMock.getAll().stream().limit(limit).collect(Collectors.toList());
        final List<ResidentDto> expected = ResidentMock.getAllDto().stream().limit(limit).collect(Collectors.toList());

        given(residentDao.getLastResidentsByRoomId(roomId, limit)).willReturn(residents);

        List<ResidentDto> actual = roomService.showLastResidents(room, limit);
        List<String> actualNames = actual
                .stream()
                .map(ResidentDto::getFirstName)
                .collect(Collectors.toList());
        List<String> expectedNames = expected
                .stream()
                .map(ResidentDto::getFirstName)
                .collect(Collectors.toList());
        assertEquals("find all result: ", expectedNames, actualNames);
        verify(residentDao, times(1)).getLastResidentsByRoomId(roomId, limit);
    }

    @Test
    void changeRoomStatusTest() throws PersistException, EntityNotFoundException {
        final Long roomId = 1L;
        final Room room101 =
            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);
        room101.setId(roomId);

        given(roomDao.findById(roomId)).willReturn(room101);
        given(roomDao.update(room101)).willReturn(room101);

        roomService.changeRoomStatus(roomId, RoomStatus.OCCUPIED);

        verify(roomDao, times(1)).findById(roomId);
        verify(roomDao, times(1)).update(room101);
    }

    @Test
    void changeRoomPriceTest() throws PersistException, EntityNotFoundException {
        final Room room101 =
            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);

        given(roomDao.findByNumber(room101.getNumber())).willReturn(room101);
        given(roomDao.update(room101)).willReturn(room101);

        roomService.changeRoomPrice(room101.getNumber(), BigDecimal.valueOf(45));

        verify(roomDao, times(1)).findByNumber(room101.getNumber());
        verify(roomDao, times(1)).update(room101);
    }

}
