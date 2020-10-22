package com.senla.hotel.dto;

public class AuthRequestDto {

    private String email;
    private String password;

    public AuthRequestDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public AuthRequestDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
