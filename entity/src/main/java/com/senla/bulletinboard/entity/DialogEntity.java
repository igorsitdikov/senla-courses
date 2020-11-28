package com.senla.bulletinboard.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "dialog")
@EqualsAndHashCode(callSuper = true)
public class DialogEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bulletin_id", insertable = false, updatable = false)
    private BulletinEntity bulletin;

    @Column(name = "bulletin_id")
    private Long bulletinId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private UserEntity customer;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

}
