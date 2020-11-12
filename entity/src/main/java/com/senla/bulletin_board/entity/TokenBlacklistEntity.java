package com.senla.bulletin_board.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "token_blacklist")
@EqualsAndHashCode(callSuper = true)
public class TokenBlacklistEntity extends AbstractEntity {

    private String token;
    @Column(name = "created_at")
    private Timestamp createdAt;
}
