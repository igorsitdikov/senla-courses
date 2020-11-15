package com.senla.bulletin_board.entity;

import com.senla.bulletin_board.enumerated.PremiumStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payment")
@EqualsAndHashCode(callSuper = true)
public class PaymentEntity extends AbstractEntity {

    private BigDecimal balance;

    @Column(name = "payed_at", insertable = false, updatable = false)
    private LocalDateTime payedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "premium", insertable = false)
    private PremiumStatus premium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff_id")
    private TariffEntity tariff;

}
