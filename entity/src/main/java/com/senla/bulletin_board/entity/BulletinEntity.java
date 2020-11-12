package com.senla.bulletin_board.entity;


import com.senla.bulletin_board.enumerated.BulletinStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "bulletin")
@EqualsAndHashCode(callSuper = true)
public class BulletinEntity extends AbstractEntity {

    @Column(name = "created_at")
    private Timestamp createdAt;
    private String title;
    private BigDecimal price;
    private String text;
    private BulletinStatus status;
}
