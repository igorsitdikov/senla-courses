package com.senla.hotel.service;

import com.senla.hotel.HotelTest;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.Gender;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.service.interfaces.RoomService;
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
public class RoomServiceImplTest {
    @Mock
    private RoomDao roomDao;
    @InjectMocks
    private RoomService roomService = new RoomServiceImpl();
    @Mock
    private ResidentDao residentDao;


    @Test
    void findRoomByIdTest() throws PersistException, EntityNotFoundException {
        final Long roomId = 1L;
        final Room room101 =
            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);
        room101.setId(roomId);

        given(roomDao.findById(roomId)).willReturn(room101);
        Room actual = roomService.findById(roomId);

        assertEquals("find by id result: ", room101, actual);
        verify(roomDao, times(1)).findById(roomId);
    }

    @Test
    void findRoomByNumberTest() throws PersistException, EntityNotFoundException {
        final Integer roomNumber = 101;
        final Room room101 =
            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);

        given(roomDao.findByNumber(roomNumber)).willReturn(room101);
        Room actual = roomService.findByNumber(roomNumber); // showRoomDetails

        assertEquals("find by number result: ", room101, actual);
        verify(roomDao, times(1)).findByNumber(roomNumber);
    }

    @Test
    void shouldReturnFindAllTest() throws PersistException {
        List<Room> rooms = new ArrayList<>();
        final Room room101 =
            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);
        final Room room102 =
            new Room(102, Stars.JUNIOR_SUIT, Accommodation.SGL_2_CHD, BigDecimal.valueOf(120), RoomStatus.VACANT);
        final Room room103 = new Room(103, Stars.SUIT, Accommodation.DBL, BigDecimal.valueOf(150), RoomStatus.VACANT);

        rooms.add(room101);
        rooms.add(room102);
        rooms.add(room103);

        given(roomDao.getAllSortedBy(SortField.DEFAULT)).willReturn(rooms);
        List<Room> expected = roomService.showAll(SortField.DEFAULT);
        assertEquals("find all result: ", expected, rooms);

        verify(roomDao, times(1)).getAllSortedBy(SortField.DEFAULT);
    }


    @Test
    void createRoomTest() throws PersistException {
        final Room room101 =
            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);
        given(roomDao.create(room101)).willReturn(room101);

        roomService.addRoom(room101);

        verify(roomDao, times(1)).create(any(Room.class));
    }

    @Test
    void showLastResidentsTest() throws PersistException, EntityNotFoundException {
        final Long roomId = 1L;
        final Integer limit = 3;
        final Room room101 =
            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);
        room101.setId(roomId);
        List<Resident> residents = new ArrayList<>();
        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
        final Resident alex = new Resident("Alex", "Smith", Gender.MALE, false, "1234569", null);
        final Resident juliet = new Resident("Juliet", "Fox", Gender.FEMALE, true, "1234568", null);

        residents.add(mike);
        residents.add(alex);
        residents.add(juliet);

        given(residentDao.getLastResidentsByRoomId(roomId, limit)).willReturn(residents);

        List<Resident> actual = roomService.showLastResidents(room101, limit);
        assertEquals("find all result: ", residents, actual);
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
