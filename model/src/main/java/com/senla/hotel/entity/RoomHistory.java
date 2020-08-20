package com.senla.hotel.entity;

import com.senla.hotel.enumerated.HistoryStatus;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RoomHistory extends AEntity {
    private Room room;
    private Resident resident;
    private List<Attendance> attendances = new LinkedList<>();
    private LocalDate checkIn;
    private LocalDate checkOut;
    private HistoryStatus status;

    public RoomHistory() {
    }

    public RoomHistory(Room room, Resident resident, LocalDate checkIn, LocalDate checkOut, HistoryStatus status) {
        this.room = room;
        this.resident = resident;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public HistoryStatus getStatus() {
        return status;
    }

    public void setStatus(HistoryStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RoomHistory that = (RoomHistory) o;
        return Objects.equals(room, that.room) &&
                Objects.equals(resident, that.resident) &&
                Objects.equals(attendances, that.attendances) &&
                Objects.equals(checkIn, that.checkIn) &&
                Objects.equals(checkOut, that.checkOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, resident, attendances, checkIn, checkOut);
    }

    @Override
    public String toString() {
        return String.format("Room's history №%d: %n" +
                        "\tRoom № %d.%n" +
                        "\t%s. %n" +
                        "\tCheck-in %s. %n" +
                        "\tCheck-out %s.",
                id,
                room.getNumber(),
                resident.toString(),
                checkIn,
                checkOut);
    }
}
