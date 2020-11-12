package com.senla.bulletin_board.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "payment")
@EqualsAndHashCode(callSuper = true)
public class SellerVoteEntity extends AbstractEntity {

    private Integer vote;
}
