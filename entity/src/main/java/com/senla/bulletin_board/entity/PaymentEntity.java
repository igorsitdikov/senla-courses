package com.senla.bulletin_board.entity;

import com.senla.bulletin_board.enumerated.PremiumStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "payment")
@EqualsAndHashCode(callSuper = true)
public class PaymentEntity extends AbstractEntity {

    private BigDecimal balance;
    @Column(name = "payed_at")
    private Timestamp payedAt;
    private PremiumStatus premium;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private TariffEntity tariff;
}
