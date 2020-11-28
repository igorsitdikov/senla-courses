package com.senla.bulletinboard.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "seller_vote")
@EqualsAndHashCode(callSuper = true)
public class SellerVoteEntity extends AbstractEntity {

    private Integer vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id", insertable = false, updatable = false)
    private UserEntity voter;

    @Column(name = "voter_id")
    private Long voterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bulletin_id", insertable = false, updatable = false)
    private BulletinEntity bulletin;

    @Column(name = "bulletin_id")
    private Long bulletinId;
}
