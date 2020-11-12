package com.senla.bulletin_board.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "comment")
@EqualsAndHashCode(callSuper = true)
public class CommentEntity extends AbstractEntity {

    @Column(name = "created_at")
    private Timestamp createdAt;
    private String text;
}
