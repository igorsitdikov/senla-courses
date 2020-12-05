package com.senla.bulletinboard.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment")
@EqualsAndHashCode(callSuper = true)
public class CommentEntity extends AbstractEntity {

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name = "bulletin_id", insertable = false, updatable = false)
    private BulletinEntity bulletin;

    @Column(name = "bulletin_id")
    private Long bulletinId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "author_id")
    private Long authorId;

}
