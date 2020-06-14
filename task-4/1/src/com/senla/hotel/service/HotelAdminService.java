package com.senla.hotel.service;

import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.RoomStatus;
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
        final Resident resident = residentService.findById(residentId);
        if (room.getStatus() == RoomStatus.VACANT) {
            final RoomHistory history = new RoomHistory(room, resident, checkIn, checkOut);
            final RoomHistory roomHistoryEntity = roomHistoryService.create(history);
            residentService.addHistoryToResident(residentId, roomHistoryEntity);
            roomService.addHistoryToRoom(roomId, roomHistoryEntity);
            roomService.update(roomId, RoomStatus.OCCUPIED);
            System.out.printf("%s was checked-in in room №%d%n",
                              resident.toString(),
                              room.getNumber());
        } else if (room.getStatus() == RoomStatus.OCCUPIED) {
            System.out.printf("Room №%d is already in used.%n", room.getNumber());
        } else {
            System.out.printf("Room №%d is repaired.%n", room.getNumber());
        }
    }

    @Override
    public void checkIn(final Resident resident, final Room room, final LocalDate checkIn, final LocalDate checkOut)
        throws NoSuchEntityException {
        checkIn(resident.getId(), room.getId(), checkIn, checkOut);
    }

    @Override
    public void checkOut(final Long residentId, final LocalDate date)
        throws NoSuchEntityException {
        final Resident resident = residentService.findById(residentId);
        final RoomHistory history = resident.getHistory();
        final Room room = history.getRoom();
        if (room.getStatus() != RoomStatus.OCCUPIED) {
            System.out.printf("The room №%d has no resident.", room.getNumber());
        } else {
            roomService.update(room.getId(), RoomStatus.VACANT);
            roomService.updateCheckOutHistory(room.getId(), history, date);
            resident.setHistory(null);
        }
    }

    @Override
    public void checkOut(final Resident resident, final LocalDate date)
        throws NoSuchEntityException {
        checkOut(resident.getId(), date);
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
    public void changeRoomStatus(final Integer number, final RoomStatus status) throws NoSuchEntityException {
        final Room room = roomService.findRoomByRoomNumber(number);
        changeRoomStatus(room.getId(), status);
    }

    @Override
    public void changeAttendancePrice(final Long id, final BigDecimal price) {
        attendanceService.updatePrice(id, price);
    }

    @Override
    public void changeAttendancePrice(final String name, final BigDecimal price) {
        attendanceService.updatePrice(name, price);
    }

    @Override
    public void changeRoomPrice(final Long id, final BigDecimal price) {
        roomService.updatePrice(id, price);
    }

    @Override
    public void changeRoomPrice(final Integer number, final BigDecimal price) {
        roomService.updatePrice(number, price);
    }

    @Override
    public void calculateBill(final Long id) throws NoSuchEntityException {
        final Resident resident = residentService.findById(id);
        if (resident.getHistory() != null) {
            final long days = ChronoUnit.DAYS.between(resident.getHistory().getCheckIn(),
                                                      resident.getHistory().getCheckOut());
            final Room room = resident.getHistory().getRoom();
            BigDecimal totalAttendances = BigDecimal.valueOf(0);
            for (int i = 0; i < resident.getHistory().getAttendances().length; i++) {
                totalAttendances = totalAttendances.add(resident.getHistory().getAttendances()[i].getPrice());
            }

            final BigDecimal total = room.getPrice()
                .multiply(new BigDecimal(days))
                .add(totalAttendances.multiply(new BigDecimal(days)));
            System.out.printf("%s has to pay %.2f BYN for the room №%d%n",
                              resident.toString(),
                              total,
                              room.getNumber());
        } else {
            System.out.printf("%s is not checked-in.%n",
                              resident.toString());
        }
    }

    @Override
    public void calculateBill(final Resident resident) throws NoSuchEntityException {
        final Long id = resident.getId();
        calculateBill(id);
    }

    @Override
    public void showVacantRoomsOnDate(final LocalDate date) {
        final Room[] vacantRooms = roomService.vacantOnDate(date);
        roomService.showRooms(vacantRooms);
    }

    @Override
    public void showLastResidents(final Long id, final Integer number) throws NoSuchEntityException {
        final RoomHistory[] histories = roomService.findRoomById(id).getHistories();
        if (histories.length > number) {
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
    public void showLastResidents(final Room room, final Integer number) throws NoSuchEntityException {
        final Long id = room.getId();
        showLastResidents(id, number);
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
        final Attendance[] attendances = attendanceService.getAttendances();
        final Attendance[] sortedAttendances =
            attendanceService.sortAttendances(attendances, new AttendanceNameComparator());
        attendanceService.showAttendances(sortedAttendances);
    }

    @Override
    public void showAttendancesSortedByPrice() {
        final Attendance[] attendances = attendanceService.getAttendances();
        final Attendance[] sortedAttendances =
            attendanceService.sortAttendances(attendances, new AttendancePriceComparator());
        attendanceService.showAttendances(sortedAttendances);
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
        final Room[] sortedRooms = roomService.sortRooms(rooms, new RoomPriceComparator());
        roomService.showRooms(sortedRooms);
    }

    @Override
    public void showAllRoomsSortedByAccommodation() {
        final Room[] rooms = roomService.getAllRooms();
        final Room[] sortedRooms = roomService.sortRooms(rooms, new RoomAccommodationComparator());
        roomService.showRooms(sortedRooms);
    }

    @Override
    public void showAllRoomsSortedByStars() {
        final Room[] rooms = roomService.getAllRooms();
        final Room[] sortedRooms = roomService.sortRooms(rooms, new RoomStarsComparator());
        roomService.showRooms(sortedRooms);
    }

    @Override
    public void showVacantRooms() {
        final Room[] rooms = roomService.getVacantRooms();
        roomService.showRooms(rooms);
    }

    @Override
    public void showVacantRoomsSortedByPrice() {
        final Room[] rooms = roomService.getVacantRooms();
        final Room[] sortedRooms = roomService.sortRooms(rooms, new RoomPriceComparator());
        roomService.showRooms(sortedRooms);
    }

    @Override
    public void showVacantRoomsSortedByAccommodation() {
        final Room[] rooms = roomService.getVacantRooms();
        final Room[] sortedRooms = roomService.sortRooms(rooms, new RoomAccommodationComparator());
        roomService.showRooms(sortedRooms);
    }

    @Override
    public void showVacantRoomsSortedByStars() {
        final Room[] rooms = roomService.getVacantRooms();
        roomService.sortRooms(rooms, new RoomStarsComparator());
        roomService.showRooms(rooms);
    }

    @Override
    public void showRoomDetails(final Long id) throws NoSuchEntityException {
        final Room room = roomService.findRoomById(id);
        System.out.println(room.toString());
    }

    @Override
    public void showRoomDetails(final Integer number) throws NoSuchEntityException {
        final Room room = roomService.findRoomByRoomNumber(number);
        System.out.println(room.toString());
    }

    @Override
    public void addResident(final Resident resident) {
        residentService.add(resident);
    }

    @Override
    public void showResidents() {
        final Resident[] residents = residentService.getResidents();
        residentService.showResidents(residents);
    }

    @Override
    public void showResidentsSortedByName() {
        final Resident[] residents = residentService.getResidents();
        final Resident[] sortedResidents = residentService.sortResidents(residents, new ResidentFullNameComparator());
        residentService.showResidents(sortedResidents);
    }

    @Override
    public void showResidentsSortedByCheckOutDate() {
        final Resident[] residents = residentService.getResidents();
        final Resident[] sortedResidents = residentService.sortResidents(residents, new ResidentCheckOutComparator());
        residentService.showResidents(sortedResidents);
    }

    @Override
    public void showCountVacantRooms() {
        final int countVacantRooms = roomService.countVacantRooms();
        System.out.println(String.format("Total vacant rooms: %d", countVacantRooms));
    }

    @Override
    public void showCountResidents() {
        final int count = residentService.showTotalNumber();
        System.out.println(String.format("Total residents: %d", count));
    }

    @Override
    public void addAttendanceToResident(final Long id, final Long attendanceId) throws NoSuchEntityException {
        residentService.addAttendanceToResident(id, attendanceId);
    }

    @Override
    public void addAttendanceToResident(final Resident resident, final Attendance attendance)
        throws NoSuchEntityException {
        residentService.addAttendanceToResident(resident, attendance);
    }
}
