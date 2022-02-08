package com.project.EStore.model.entity;

import com.project.EStore.model.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class UserEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 50)
    private String username;
    @Column(nullable = false, length = 50)
    private String password;
    @Column(nullable = false)
    private LocalDateTime createdOn;
    @ManyToMany
    private Set<RoleEntity> roles;

    @PrePersist
    private void initCreatedOn(){
        this.createdOn = LocalDateTime.now();
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }
}
