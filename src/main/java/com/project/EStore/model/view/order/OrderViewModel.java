package com.project.EStore.model.view.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
public class OrderViewModel {

    private Integer id;
    private String created;
    private Set<OrderDetailViewModel> orderDetails;
    private String totalPrice;

    public OrderViewModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public OrderViewModel setCreated(String created) {
        this.created = created;
        return this;
    }

    public OrderViewModel setOrderDetails(Set<OrderDetailViewModel> orderDetails) {
        this.orderDetails = orderDetails;
        return this;
    }

    public OrderViewModel setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
}
