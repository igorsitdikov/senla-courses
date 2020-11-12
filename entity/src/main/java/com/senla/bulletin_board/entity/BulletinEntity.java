package com.senla.bulletin_board.entity;


import com.senla.bulletin_board.enumerated.BulletinStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "bulletin")
@EqualsAndHashCode(callSuper = true)
public class BulletinEntity extends AbstractEntity {

    @Column(name = "created_at")
    private Timestamp createdAt;
    private String title;
    private BigDecimal price;
    private String text;
    private BulletinStatus status;
    @OneToMany(mappedBy="bulletin")
    private Set<CommentEntity> comments;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private UserEntity seller;
}
