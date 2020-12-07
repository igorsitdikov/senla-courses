package com.senla.bulletinboard.entity;


import com.senla.bulletinboard.enumerated.BulletinStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "bulletin")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BulletinEntity extends AbstractEntity {

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    private String title;

    private BigDecimal price;

    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", insertable = false)
    private BulletinStatus status;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "bulletin", fetch = FetchType.LAZY)
    private Set<CommentEntity> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    private UserEntity seller;

    @Column(name = "seller_id")
    private Long sellerId;

    @OneToMany(mappedBy = "bulletin", fetch = FetchType.LAZY)
    private Set<SellerVoteEntity> sellerVoteEntities;

}
