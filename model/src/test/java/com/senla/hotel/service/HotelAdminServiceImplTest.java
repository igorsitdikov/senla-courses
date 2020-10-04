//package com.senla.hotel.service;
//
//import com.senla.hotel.HotelTest;
//import com.senla.hotel.dao.interfaces.ResidentDao;
//import com.senla.hotel.dao.interfaces.RoomDao;
//import com.senla.hotel.dao.interfaces.RoomHistoryDao;
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
//import com.senla.hotel.service.interfaces.HotelAdminService;
//import com.senla.hotel.utils.HibernateUtil;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.util.AssertionErrors.assertEquals;
//
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = HotelTest.class)
//public class HotelAdminServiceImplTest {
//    @Mock
//    private RoomDao roomDao;
//    @Mock
//    private ResidentDao residentDao;
//    @Mock
//    private RoomHistoryDao roomHistoryDao;
//    @Mock
//    private HibernateUtil hibernateUtil;
//    @InjectMocks
//    private HotelAdminService hotelAdminService = new HotelAdminServiceImpl();
//    @Autowired
//    private HibernateUtil hibernate;
//
//    @Test
//    void calculateBillTest() throws PersistException, EntityNotFoundException {
//        final Long residentId = 1L;
//        final Long historyId = 1L;
//        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
//        Set<RoomHistory> histories = new HashSet<>();
//        final Room room101 =
//            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);
//        final RoomHistory roomHistory =
//            new RoomHistory(room101, mike, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
//                            HistoryStatus.CHECKED_IN);
//        roomHistory.setId(historyId);
//        histories.add(roomHistory);
//        mike.setHistory(histories);
//        given(residentDao.findById(residentId)).willReturn(mike);
//        final BigDecimal price = BigDecimal.valueOf(2000.45);
//        given(roomHistoryDao.calculateBill(historyId)).willReturn(price);
//
//        BigDecimal actual = hotelAdminService.calculateBill(historyId);
//        assertEquals("find bill by id result: ", price, actual);
//
//        verify(residentDao, times(1)).findById(residentId);
//        verify(roomHistoryDao, times(1)).calculateBill(historyId);
//    }
//
//    @Test
//    void checkInTest() throws PersistException, SQLException, EntityNotFoundException {
//        final Long residentId = 1L;
//        final Long roomId = 1L;
//        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
//        mike.setId(residentId);
//        final Room room101 =
//            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT);
//        room101.setId(roomId);
//        final RoomHistory roomHistory =
//            new RoomHistory(room101, mike, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
//                            HistoryStatus.CHECKED_IN);
//
//        given(hibernateUtil.openSession()).willReturn(hibernate.openSession());
//        given(residentDao.findById(residentId)).willReturn(mike);
//        given(roomDao.findById(roomId)).willReturn(room101);
//        given(roomDao.update(room101)).willReturn(room101);
//        given(roomHistoryDao.create(roomHistory)).willReturn(roomHistory);
//
//        hotelAdminService.checkIn(mike, room101, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5));
//        verify(residentDao, times(1)).findById(residentId);
//        verify(roomDao, times(1)).findById(roomId);
//        verify(roomDao, times(1)).update(room101);
//        verify(roomHistoryDao, times(1)).create(roomHistory);
//    }
//
//    @Test
//    void checkOutTest() throws PersistException, SQLException, EntityNotFoundException {
//        final Long residentId = 1L;
//        final Long roomId = 1L;
//        final Resident mike = new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null);
//        mike.setId(residentId);
//        final Room room101 =
//            new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.OCCUPIED);
//        room101.setId(roomId);
//        final RoomHistory roomHistory =
//            new RoomHistory(room101, mike, LocalDate.of(2020, 8, 3), LocalDate.of(2020, 8, 5),
//                            HistoryStatus.CHECKED_IN);
//        Set<RoomHistory> histories = new HashSet<>();
//        histories.add(roomHistory);
//        mike.setHistory(histories);
//        given(hibernateUtil.openSession()).willReturn(hibernate.openSession());
//        given(residentDao.findById(residentId)).willReturn(mike);
//        given(roomDao.update(room101)).willReturn(room101);
//        given(roomHistoryDao.update(roomHistory)).willReturn(roomHistory);
//
//        hotelAdminService.checkOut(mike, LocalDate.of(2020, 8, 5));
//        verify(residentDao, times(1)).findById(residentId);
//        verify(roomDao, times(1)).update(room101);
//        verify(roomHistoryDao, times(1)).update(roomHistory);
//    }
//}
