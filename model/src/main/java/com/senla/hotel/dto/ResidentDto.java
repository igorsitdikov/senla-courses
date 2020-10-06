package com.senla.hotel.dto;

import com.senla.hotel.enumerated.Gender;

import java.util.Objects;
import java.util.Set;

public class ResidentDto extends ADto {

    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Boolean vip;
    private String phone;
    private Set<RoomHistoryDto> historyDtos;

    public ResidentDto() {
    }

    public ResidentDto(final Long id,
                        final String firstName,
                        final String lastName,
                        final Gender gender,
                        final Boolean vip,
                        final String phone,
                        final Set<RoomHistoryDto> historyDtos) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.vip = vip;
        this.phone = phone;
        this.historyDtos = historyDtos;
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

    public Set<RoomHistoryDto> getHistoryDtos() {
        return historyDtos;
    }

    public void setHistoryDtos(final Set<RoomHistoryDto> historyDtos) {
        this.historyDtos = historyDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResidentDto that = (ResidentDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                gender == that.gender &&
                Objects.equals(vip, that.vip) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(historyDtos, that.historyDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, gender, vip, phone, historyDtos);
    }
}
