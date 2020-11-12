package com.senla.bulletin_board.entity;

import com.senla.bulletin_board.enumerated.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

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
    private UserRole role;
    // mb not working
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    private Set<CommentEntity> comments;
}
