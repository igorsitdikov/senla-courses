package com.senla.bulletin_board.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "dialog")
@EqualsAndHashCode(callSuper = true)
public class DialogEntity extends AbstractEntity {

}
