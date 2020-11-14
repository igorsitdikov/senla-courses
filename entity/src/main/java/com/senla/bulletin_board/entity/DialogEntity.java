package com.senla.bulletin_board.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "dialog")
@EqualsAndHashCode(callSuper = true)
public class DialogEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "bulletin_id")
    private BulletinEntity bulletin;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private UserEntity customer;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private MessageEntity message;

}
