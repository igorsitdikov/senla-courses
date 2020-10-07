package com.senla.hotel.dto;

import java.time.LocalDate;

public class CheckInDto {

    private ResidentDto resident;
    private RoomDto room;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public CheckInDto(ResidentDto resident, RoomDto room, LocalDate checkIn, LocalDate checkOut) {
        this.resident = resident;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public ResidentDto getResident() {
        return resident;
    }

    public void setResident(ResidentDto resident) {
        this.resident = resident;
    }

    public RoomDto getRoom() {
        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
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
}
