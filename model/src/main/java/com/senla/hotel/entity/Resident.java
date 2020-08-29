package com.senla.hotel.entity;

import com.senla.hotel.enumerated.Gender;

import java.util.Objects;

public class Resident extends AEntity {

    private String firstName;
    private String lastName;
    private Gender gender;
    private Boolean vip;
    private String phone;
    private RoomHistory history;

    public Resident() {
    }

    public Resident(final String firstName,
                    final String lastName,
                    final Gender gender,
                    final Boolean vip,
                    final String phone,
                    final RoomHistory history) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.vip = vip;
        this.phone = phone;
        this.history = history;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(final Boolean vip) {
        this.vip = vip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public RoomHistory getHistory() {
        return history;
    }

    public void setHistory(final RoomHistory history) {
        this.history = history;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Resident resident = (Resident) o;
        return Objects.equals(firstName, resident.firstName) &&
               Objects.equals(lastName, resident.lastName) &&
               gender == resident.gender &&
               Objects.equals(vip, resident.vip) &&
               Objects.equals(phone, resident.phone) &&
               Objects.equals(history, resident.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, gender, vip, phone, history);
    }

    @Override
    public String toString() {
        return String.format("Resident %s %s", firstName, lastName);
    }
}
