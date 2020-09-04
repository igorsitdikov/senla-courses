package com.senla.hotel.entity;

import com.senla.hotel.enumerated.Gender;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "resident")
public class Resident extends AEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "gender")
    private Gender gender;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "vip", nullable = false)
    private Boolean vip;
    @Column(name = "phone")
    private String phone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "history_id", referencedColumnName = "id")
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

    @Override
    public Long getId() {
        return id;
    }

    @Override
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
