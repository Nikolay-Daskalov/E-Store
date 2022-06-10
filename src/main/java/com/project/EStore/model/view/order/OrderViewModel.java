package com.project.EStore.model.view.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
public class OrderViewModel {

    private Integer id;
    private LocalDateTime created;
    private Set<OrderDetailViewModel> orderDetails;

    public OrderViewModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public OrderViewModel setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public OrderViewModel setOrderDetails(Set<OrderDetailViewModel> orderDetails) {
        this.orderDetails = orderDetails;
        return this;
    }
}
