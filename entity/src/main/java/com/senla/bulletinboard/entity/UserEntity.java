package com.senla.bulletinboard.entity;

import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import com.senla.bulletinboard.enumerated.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "role")
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "premium")
    private PremiumStatus premium;

    @Enumerated(EnumType.STRING)
    @Column(name = "auto_subscribe")
    private AutoSubscribeStatus autoSubscribe;

    @Column(name = "balance")
    private BigDecimal balance;

    @Setter(value = AccessLevel.PRIVATE)
    @Formula("(select avg(sv.vote) from seller_vote sv join bulletin b on b.id = sv.bulletin_id where b.seller_id = id)")
    private Double rating;

    @PostLoad
    public void calculateRating() {
        if (this.rating == null) {
            this.rating = 0.0;
        }
    }

    @PrePersist
    public void prePersist() {
        role = UserRole.USER;
        premium = PremiumStatus.DISABLE;
        balance = BigDecimal.ZERO;
        autoSubscribe = AutoSubscribeStatus.DISABLE;
    }
}
