package com.senla.hotel.entity;

import java.time.LocalDate;

public class RoomHistory extends AbstractEntity {
    private Long roomId;
    private Long residentId;
    private Attendance[] attendances;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public RoomHistory() {
        attendances = new Attendance[0];
    }

    public RoomHistory(final Long roomId, final Long residentId, final LocalDate checkIn, final LocalDate checkOut) {
        this.roomId = roomId;
        this.residentId = residentId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        attendances = new Attendance[0];
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(final Long roomId) {
        this.roomId = roomId;
    }

    public Long getResidentId() {
        return residentId;
    }

    public void setResidentId(final Long residentId) {
        this.residentId = residentId;
    }

    public Attendance[] getAttendances() {
        return attendances;
    }

    public void setAttendances(final Attendance[] attendances) {
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
        if (!roomId.equals(other.getRoomId())) {
            return false;
        }
        if (!residentId.equals(other.getResidentId())) {
            return false;
        }
        if (attendances.length != other.getAttendances().length) {
            return false;
        }
        if (!checkIn.equals(other.getCheckIn())) {
            return false;
        }
        return checkOut.equals(other.getCheckOut());
    }

    @Override
    public String toString() {
        return String.format("Room history №%d %n" +
                             "Room: № %d %n" +
                             "\t%s. %n" +
                             "\tCheck-in: %s. %n" +
                             "\tCheck-out: %s", id, roomId, residentId.toString(), checkIn, checkOut);
    }
}
