package com.project.EStore.model.binding;

import com.project.EStore.model.entity.enums.RoleEnum;
import com.project.EStore.util.validation.annotation.NoSpecialCharacters;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
public class UserRoleBindingModel {

    @NoSpecialCharacters
    private String username;
    private Set<RoleEnum> roles;

    public UserRoleBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserRoleBindingModel setRoles(Set<RoleEnum> roles) {
        this.roles = roles;
        return this;
    }
}
