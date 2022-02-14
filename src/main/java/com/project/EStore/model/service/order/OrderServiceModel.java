package com.project.EStore.model.service.order;

import com.project.EStore.model.service.user.UserServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
public class OrderServiceModel {

    private Integer id;
    private UserServiceModel user;
    private LocalDateTime created;
    private Set<OrderDetailServiceModel> orderDetails;

    public OrderServiceModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public OrderServiceModel setUser(UserServiceModel user) {
        this.user = user;
        return this;
    }

    public OrderServiceModel setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public OrderServiceModel setOrderDetails(Set<OrderDetailServiceModel> orderDetails) {
        this.orderDetails = orderDetails;
        return this;
    }
}
