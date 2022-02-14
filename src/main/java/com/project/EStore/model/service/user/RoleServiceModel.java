package com.project.EStore.model.service.user;

import com.project.EStore.model.entity.enums.RoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
public class RoleServiceModel {

    private RoleEnum role;
    private Set<UserServiceModel> users;


    public RoleServiceModel setRole(RoleEnum role) {
        this.role = role;
        return this;
    }

    public RoleServiceModel setUsers(Set<UserServiceModel> users) {
        this.users = users;
        return this;
    }
}
