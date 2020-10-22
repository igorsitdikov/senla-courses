package com.senla.hotel.dto;

import com.senla.hotel.enumerated.UserRole;

public class UserDto extends ADto {

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private UserRole role;

    public UserDto() {
    }

    public UserDto(final Long id,
                    final String firstName,
                    final String lastName,
                    final String password,
                    final String email,
                    final String phone,
                    final UserRole role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(final UserRole role) {
        this.role = role;
    }
}
