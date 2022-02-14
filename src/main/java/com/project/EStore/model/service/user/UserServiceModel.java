package com.project.EStore.model.service.user;

import com.project.EStore.model.service.order.OrderServiceModel;

import java.time.LocalDateTime;
import java.util.Set;

public class UserServiceModel {

    private Integer id;
    private String username;
    private String password;
    private LocalDateTime createdOn;
    private Set<RoleServiceModel> roles;
    private Set<OrderServiceModel> orders;


    public UserServiceModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public UserServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserServiceModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public UserServiceModel setRoles(Set<RoleServiceModel> roles) {
        this.roles = roles;
        return this;
    }

    public UserServiceModel setOrders(Set<OrderServiceModel> orders) {
        this.orders = orders;
        return this;
    }
}
