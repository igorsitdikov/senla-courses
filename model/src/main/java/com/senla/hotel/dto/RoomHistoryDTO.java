package com.senla.hotel.dto;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class RoomHistoryDTO extends ADataTransferObject {
    private RoomDTO room;
    private ResidentDTO resident;
    private List<AttendanceDTO> attendances = new LinkedList<>();
    private LocalDate checkIn;
    private LocalDate checkOut;

    public RoomHistoryDTO() {
    }

    public RoomHistoryDTO(final RoomDTO room,
                          final ResidentDTO resident,
                          final LocalDate checkIn,
                          final LocalDate checkOut) {
        this.room = room;
        this.resident = resident;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(final RoomDTO room) {
        this.room = room;
    }

    public ResidentDTO getResident() {
        return resident;
    }

    public void setResident(final ResidentDTO resident) {
        this.resident = resident;
    }

    public List<AttendanceDTO> getAttendances() {
        return attendances;
    }

    public void setAttendances(final List<AttendanceDTO> attendances) {
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
        final RoomHistoryDTO other = (RoomHistoryDTO) obj;
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
