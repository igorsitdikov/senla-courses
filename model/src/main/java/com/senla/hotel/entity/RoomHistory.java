package com.senla.hotel.entity;

import com.senla.hotel.enumerated.HistoryStatus;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/*
 * TODO: add DTO for all entities
 * */
@Entity
@Table(name = "history")
public class RoomHistory extends AEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;
    @ManyToOne
//    @JoinColumn(name="cart_id", nullable=false)
//    @JoinTable(name = "resident", joinColumns = {@JoinColumn(name = "resident_id")}, inverseJoinColumns = {
//        @JoinColumn(name = "id", unique = true)})
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;
    @ManyToMany
    @JoinTable(name = "histories_attendances", joinColumns = {@JoinColumn(name = "history_id")}, inverseJoinColumns = {
            @JoinColumn(name = "attendance_id")})
    private List<Attendance> attendances = new LinkedList<>();
    @Column(name = "check_in")
    private Date checkIn;
    @Column(name = "check_out")
    private Date checkOut;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private HistoryStatus status;

    public RoomHistory() {
    }

    public RoomHistory(Room room, Resident resident, LocalDate checkIn, LocalDate checkOut, HistoryStatus status) {
        this.room = room;
        this.resident = resident;
        this.checkIn = Date.valueOf(checkIn);
        this.checkOut = Date.valueOf(checkOut);
        this.status = status;
    }

    public void addAttendance(Attendance attendance) {
        this.attendances.add(attendance);
    }

    public void removeAttendance(Attendance attendance) {
        this.attendances.remove(attendance);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
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
        return checkIn.toLocalDate();
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = Date.valueOf(checkIn);
    }

    public LocalDate getCheckOut() {
        return checkOut.toLocalDate();
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = Date.valueOf(checkOut);
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
