package com.senla.bulletin_board.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "message")
@EqualsAndHashCode(callSuper = true)
public class MessageEntity extends AbstractEntity {

    private String text;
    private Timestamp createdAt;
}
