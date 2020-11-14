package com.senla.bulletin_board.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "payment")
@EqualsAndHashCode(callSuper = true)
public class SellerVoteEntity extends AbstractEntity {

    private Integer vote;

    @ManyToOne
    @JoinColumn(name = "voter_id")
    private UserEntity voter;

    @ManyToOne
    @JoinColumn(name = "bulletin_id")
    private BulletinEntity bulletin;

}
