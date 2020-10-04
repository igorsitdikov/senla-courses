package com.senla.hotel.dto;

import com.senla.hotel.enumerated.HistoryStatus;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class RoomHistoryDto extends ADto {

    private Long id;
    private RoomDto roomDto;
    private ResidentDto residentDto;
    private List<AttendanceDto> attendancesDto = new LinkedList<>();
    private LocalDate checkIn;
    private LocalDate checkOut;
    private HistoryStatus status;

    public RoomHistoryDto() {
    }

    public RoomHistoryDto(final Long id,
                           final RoomDto roomDto,
                           final ResidentDto residentDto,
                           final List<AttendanceDto> attendancesDto,
                           final LocalDate checkIn,
                           final LocalDate checkOut,
                           final HistoryStatus status) {
        this.id = id;
        this.roomDto = roomDto;
        this.residentDto = residentDto;
        this.attendancesDto = attendancesDto;
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

    public RoomDto getRoomDto() {
        return roomDto;
    }

    public void setRoomDto(final RoomDto roomDto) {
        this.roomDto = roomDto;
    }

    public ResidentDto getResidentDto() {
        return residentDto;
    }

    public void setResidentDto(final ResidentDto residentDto) {
        this.residentDto = residentDto;
    }

    public List<AttendanceDto> getAttendancesDto() {
        return attendancesDto;
    }

    public void setAttendancesDto(final List<AttendanceDto> attendancesDto) {
        this.attendancesDto = attendancesDto;
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

    public HistoryStatus getStatus() {
        return status;
    }

    public void setStatus(final HistoryStatus status) {
        this.status = status;
    }
}