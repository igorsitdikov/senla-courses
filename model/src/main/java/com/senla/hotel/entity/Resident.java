package com.senla.hotel.entity;

import com.senla.hotel.enumerated.Gender;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "resident")
public class Resident extends AEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "vip", nullable = false)
    private Boolean vip;
    @Column(name = "phone")
    private String phone;
    @OneToMany(mappedBy = "resident", fetch = FetchType.LAZY)
    @Where(clause = "status = 'CHECKED_IN'")
    private Set<RoomHistory> history;

    public Resident() {
    }

    public Resident(final String firstName,
                    final String lastName,
                    final Gender gender,
                    final Boolean vip,
                    final String phone,
                    final Set<RoomHistory> history) {
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

    public Set<RoomHistory> getHistory() {
        return history;
    }

    public void setHistory(final Set<RoomHistory> history) {
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
