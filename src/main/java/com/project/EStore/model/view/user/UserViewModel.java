package com.project.EStore.model.view.user;

import com.project.EStore.model.entity.enums.RoleEnum;
import com.project.EStore.model.view.order.OrderViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;


@NoArgsConstructor
@Getter
public class UserViewModel {

    private Integer id;
    private String username;
    private String createdOn;
    private Set<RoleEnum> roles;
    private Set<OrderViewModel> orders;

    public UserViewModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserViewModel setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public UserViewModel setRoles(Set<RoleEnum> roles) {
        this.roles = roles;
        return this;
    }

    public UserViewModel setOrders(Set<OrderViewModel> orders) {
        this.orders = orders;
        return this;
    }
}
