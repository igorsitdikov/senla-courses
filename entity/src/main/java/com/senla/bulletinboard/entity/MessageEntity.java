package com.senla.bulletinboard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageEntity extends AbstractEntity {

    private String text;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false, insertable = false, updatable = false)
    private UserEntity sender;

    @Column(name = "sender_id")
    private Long senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false, insertable = false, updatable = false)
    private UserEntity recipient;

    @Column(name = "recipient_id")
    private Long recipientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dialog_id", nullable = false, insertable = false, updatable = false)
    private DialogEntity dialog;

    @Column(name = "dialog_id")
    private Long dialogId;
}
