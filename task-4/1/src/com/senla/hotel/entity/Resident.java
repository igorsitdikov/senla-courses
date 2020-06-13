package com.senla.hotel.entity;

import com.senla.hotel.entity.type.Gender;

public class Resident extends AbstractEntity {
    private String firstName;
    private String lastName;
    private Gender gender;
    private Boolean vip;
    private String phone;
    private RoomHistory history;

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
    public String toString() {
        return String.format("Resident %s %s", firstName, lastName);
    }
}
