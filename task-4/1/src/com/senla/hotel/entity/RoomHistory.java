package com.senla.hotel.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomHistory extends AEntity {
    private Room room;
    private Resident resident;
    private List<Attendance> attendances;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public RoomHistory() {
        attendances = new ArrayList<>();
    }

    public RoomHistory(final Room room, final Resident resident, final LocalDate checkIn, final LocalDate checkOut) {
        this.room = room;
        this.resident = resident;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        attendances = new ArrayList<>();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(final Room room) {
        this.room = room;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(final Resident resident) {
        this.resident = resident;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(final List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(final LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(final LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoomHistory other = (RoomHistory) obj;
        if (!room.equals(other.getRoom())) {
            return false;
        }
        if (!resident.equals(other.getResident())) {
            return false;
        }
        if (attendances.size() != other.getAttendances().size()) {
            return false;
        }
        if (!checkIn.equals(other.getCheckIn())) {
            return false;
        }
        return checkOut.equals(other.getCheckOut());
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
