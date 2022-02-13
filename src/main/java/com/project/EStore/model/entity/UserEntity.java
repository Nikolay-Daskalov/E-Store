package com.project.EStore.model.entity;

import com.project.EStore.model.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
public class UserEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 50)
    private String username;
    @Column(nullable = false, length = 200)
    private String password;
    @Column(nullable = false)
    private LocalDateTime createdOn;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<RoleEntity> roles;
    @OneToMany(mappedBy = "user")
    private Set<OrderEntity> orders;

    public UserEntity() {
        this.roles = new HashSet<>();
        this.orders = new HashSet<>();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
