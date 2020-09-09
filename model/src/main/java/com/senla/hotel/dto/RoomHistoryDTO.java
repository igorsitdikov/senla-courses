package com.senla.hotel.dto;

import com.senla.hotel.enumerated.HistoryStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/*
 * TODO: add DTO for all entities
 * */
public class RoomHistoryDTO {

    private Long id;
    private RoomDTO room;
    private ResidentDTO resident;
    private List<AttendanceDTO> attendances = new LinkedList<>();
    private LocalDate checkIn;
    private LocalDate checkOut;
    private HistoryStatus status;

    public RoomHistoryDTO() {
    }

    public RoomHistoryDTO(RoomDTO room, ResidentDTO resident, LocalDate checkIn, LocalDate checkOut, HistoryStatus status) {
        this.room = room;
        this.resident = resident;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public ResidentDTO getResident() {
        return resident;
    }

    public void setResident(ResidentDTO resident) {
        this.resident = resident;
    }

    public List<AttendanceDTO> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AttendanceDTO> attendances) {
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
        final RoomHistoryDTO that = (RoomHistoryDTO) o;
        return Objects.equals(room, that.room) &&
                Objects.equals(resident, that.resident) &&
                Objects.equals(attendances, that.attendances) &&
                Objects.equals(checkIn, that.checkIn) &&
                Objects.equals(checkOut, that.checkOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, attendances, checkIn, checkOut);
    }

    @Override
    public String toString() {
        return String.format("Room's history №%d: %n" +
                        "\tRoom № %d.%n" +
//                        "\t%s. %n" +
                        "\tCheck-in %s. %n" +
                        "\tCheck-out %s.",
                id,
                room.getNumber(),
//                resident.toString(),
                checkIn,
                checkOut);
    }
}
