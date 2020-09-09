package com.senla.hotel.dto;

import com.senla.hotel.enumerated.Gender;

import java.util.Objects;
import java.util.Set;

public class ResidentDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Boolean vip;
    private String phone;
    private Set<RoomHistoryDTO> history;

    public ResidentDTO() {
    }

    public ResidentDTO(final String firstName,
                       final String lastName,
                       final Gender gender,
                       final Boolean vip,
                       final String phone,
                       final Set<RoomHistoryDTO> history) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.vip = vip;
        this.phone = phone;
        this.history = history;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public Set<RoomHistoryDTO> getHistory() {
        return history;
    }

    public void setHistory(final Set<RoomHistoryDTO> history) {
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
        final ResidentDTO resident = (ResidentDTO) o;
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
