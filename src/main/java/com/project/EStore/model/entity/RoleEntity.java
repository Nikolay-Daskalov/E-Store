package com.project.EStore.model.entity;

import com.project.EStore.model.entity.base.BaseEntity;
import com.project.EStore.model.entity.enums.RoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
public class RoleEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    public RoleEntity setRole(RoleEnum role) {
        this.role = role;
        return this;
    }

    public RoleEntity setUsers(Set<UserEntity> users) {
        this.users = users;
        return this;
    }
}
