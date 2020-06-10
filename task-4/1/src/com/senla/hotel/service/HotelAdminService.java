package com.senla.hotel.service;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.entity.type.RoomStatus;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.service.interfaces.IHotelAdminService;
import com.senla.hotel.utils.comparator.AttendanceNameComparator;
import com.senla.hotel.utils.comparator.AttendancePriceComparator;
import com.senla.hotel.utils.comparator.ResidentCheckOutComparator;
import com.senla.hotel.utils.comparator.ResidentFullNameComparator;
import com.senla.hotel.utils.comparator.RoomAccommodationComparator;
import com.senla.hotel.utils.comparator.RoomPriceComparator;
import com.senla.hotel.utils.comparator.RoomStarsComparator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class HotelAdminService implements IHotelAdminService {
    private final RoomService roomService = new RoomService();
    private final ResidentService residentService = new ResidentService();
    private final AttendanceService attendanceService = new AttendanceService();
    private final RoomHistoryService roomHistoryService = new RoomHistoryService();

    @Override
    public void checkIn(final Long residentId, final Long roomId, final LocalDate checkIn, final LocalDate checkOut)
        throws NoSuchEntityException {
        final Room room = roomService.findRoomById(roomId);
        if (room.getStatus() == RoomStatus.VACANT) {
            final RoomHistory history = new RoomHistory(roomId, residentId, checkIn, checkOut);
            final RoomHistory roomHistoryEntity = roomHistoryService.create(history);
            residentService.addHistoryToResident(residentId, roomHistoryEntity);
            roomService.addHistoryToRoom(roomId, roomHistoryEntity);
            roomService.update(roomId, RoomStatus.OCCUPIED);
            final Resident resident = residentService.findById(residentId);
            System.out.printf("%s was checked-in in room №%d%n",
                              resident.fullName(),
                              room.getNumber());
        } else if (room.getStatus() == RoomStatus.OCCUPIED) {
            System.out.printf("Room №%d is already in used.%n", room.getNumber());
        } else {
            System.out.printf("Room №%d is repaired.%n", room.getNumber());
        }
    }

    @Override
    public void checkOut(final Long residentId, final Long roomId) throws NoSuchEntityException {
        final Room room = roomService.findRoomById(roomId);
        if (room.getStatus() != RoomStatus.OCCUPIED) {
            System.out.printf("The room №%d has no resident.", room.getNumber());
        } else {
            roomService.update(roomId, RoomStatus.VACANT);
            final Resident resident = residentService.findById(residentId);
            resident.setHistory(null);
        }
    }

    @Override
    public void changeRoomStatus(final Long id, final RoomStatus status) throws NoSuchEntityException {
        final Room room = roomService.findRoomById(id);
        if (room.getStatus() == RoomStatus.OCCUPIED && status == RoomStatus.OCCUPIED) {
            System.out.println("Room is already occupied");
        } else if (room.getStatus() == RoomStatus.REPAIR && status == RoomStatus.OCCUPIED) {
            System.out.println("Room is not available now");
        } else {
            room.setStatus(status);
        }
    }

    @Override
    public void calculateBill(final Long id) throws NoSuchEntityException {
        final Resident resident = residentService.findById(id);
        if (resident.getHistory() != null) {
            final long days = ChronoUnit.DAYS.between(resident.getHistory().getCheckIn(),
                                                      resident.getHistory().getCheckOut());
            final Room room = roomService.findRoomById(resident.getHistory().getRoomId());
            BigDecimal totalAttendances = BigDecimal.valueOf(0);
            for (int i = 0; i < resident.getHistory().getAttendances().length; i++) {
                totalAttendances = totalAttendances.add(resident.getHistory().getAttendances()[i].getPrice());
            }

            final BigDecimal total = room.getPrice()
                .multiply(new BigDecimal(days))
                .add(totalAttendances.multiply(new BigDecimal(days)));
            System.out.printf("%s has to pay %.2f BYN for the room №%d%n",
                              resident.fullName(),
                              total,
                              room.getNumber());
        } else {
            System.out.printf("%s is not checked-in.%n",
                              resident.fullName());
        }
    }

    @Override
    public void showLastResidents(final Long id, final Integer number) throws NoSuchEntityException {
        final RoomHistory[] histories = roomService.findRoomById(id).getHistories();
        if (histories.length > number) {
            System.out.println("больше");
            for (int i = histories.length - number; i < histories.length; i++) {
                System.out.println(roomService.findRoomById(id).getHistories()[i].toString());
            }
        } else {
            for (int i = 0; i < histories.length; i++) {
                System.out.println(roomService.findRoomById(id).getHistories()[i].toString());
            }
        }
    }

    @Override
    public void createAttendance(final Attendance attendance) {
        attendanceService.addAttendance(attendance);
    }

    @Override
    public void showAttendances() {
        attendanceService.showAllAttendances();
    }

    @Override
    public void showAttendancesSortedByName() {
        attendanceService.sortAttendances(attendanceService.getAttendances(), new AttendanceNameComparator());
        showAttendances();
    }

    @Override
    public void showAttendancesSortedByPrice() {
        attendanceService.sortAttendances(attendanceService.getAttendances(), new AttendancePriceComparator());
        showAttendances();
    }

    @Override
    public void addRoom(final Room room) {
        roomService.add(room);
    }

    @Override
    public void showAllRooms() {
        final Room[] rooms = roomService.getAllRooms();
        roomService.showRooms(rooms);
    }

    @Override
    public void showAllRoomsSortedByPrice() {
        final Room[] rooms = roomService.getAllRooms();
        roomService.sortRooms(rooms, new RoomPriceComparator());
        roomService.showRooms(rooms);
    }

    @Override
    public void showAllRoomsSortedByAccommodation() {
        final Room[] rooms = roomService.getAllRooms();
        roomService.sortRooms(rooms, new RoomAccommodationComparator());
        roomService.showRooms(rooms);
    }

    @Override
    public void showAllRoomsSortedByStars() {
        final Room[] rooms = roomService.getAllRooms();
        roomService.sortRooms(rooms, new RoomStarsComparator());
        roomService.showRooms(rooms);
    }

    @Override
    public void showVacantRooms() {
        final Room[] rooms = roomService.getVacantRooms();
        roomService.showRooms(rooms);
    }

    @Override
    public void showVacantRoomsSortedByPrice() {
        final Room[] rooms = roomService.getVacantRooms();
        roomService.sortRooms(rooms, new RoomPriceComparator());
        roomService.showRooms(rooms);
    }

    @Override
    public void showVacantRoomsSortedByAccommodation() {
        final Room[] rooms = roomService.getVacantRooms();
        roomService.sortRooms(rooms, new RoomAccommodationComparator());
        roomService.showRooms(rooms);
    }

    @Override
    public void showVacantRoomsSortedByStars() {
        final Room[] rooms = roomService.getVacantRooms();
        roomService.sortRooms(rooms, new RoomStarsComparator());
        roomService.showRooms(rooms);
    }

    @Override
    public void showRoomDetails(final Long id) throws NoSuchEntityException {
        roomService.showDetails(id);
    }

    @Override
    public void addResident(final Resident resident) {
        residentService.add(resident);
    }

    @Override
    public void showResidents() {
        residentService.showResidents(residentService.getResidents());
    }

    @Override
    public void showResidentsSortedByName() {
        final Resident[] residents = residentService.getResidents();
        residentService.showResidents(residentService.sortResidents(residents, new ResidentFullNameComparator()));
    }

    @Override
    public void showResidentsSortedByCheckOutDate() {
        final Resident[] residents = residentService.getResidents();
        residentService.showResidents(residentService.sortResidents(residents, new ResidentCheckOutComparator()));
    }

    @Override
    public void showCountVacantRooms() {
        System.out.println(roomService.countVacantRooms());
    }

    @Override
    public void showCountResidents() {
        System.out.println(residentService.showTotalNumber());
    }

    @Override
    public void addAttendanceToResident(Long id, Attendance attendance) throws NoSuchEntityException {
        residentService.addAttendanceToResident(id, attendance);
    }
}
