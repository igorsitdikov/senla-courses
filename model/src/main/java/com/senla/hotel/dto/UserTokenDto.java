package com.senla.hotel.dto;

public class UserTokenDto {

    private String token;

    public UserTokenDto() {
    }

    public UserTokenDto(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
