package com.senla.hotel.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "token")
public class TokenEntity extends AEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;

    public TokenEntity() {
    }

    public TokenEntity(String token) {
        this.token = token;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
