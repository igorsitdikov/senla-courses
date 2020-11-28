package com.senla.bulletinboard.entity;

import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import com.senla.bulletinboard.enumerated.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends AbstractEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", insertable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "premium", insertable = false)
    private PremiumStatus premium;

    @Enumerated(EnumType.STRING)
    @Column(name = "auto_subscribe", insertable = false)
    private AutoSubscribeStatus autoSubscribe;

    @Column(name = "balance", insertable = false)
    private BigDecimal balance;
    // mb not working
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private Set<PaymentEntity> payments;

}
