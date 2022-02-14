package com.project.EStore.model.service.order;

import com.project.EStore.model.service.product.ProductServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderDetailServiceModel {

    private Integer id;
    private ProductServiceModel product;
    private Short quantity;
    private OrderServiceModel orderNumber;

    public OrderDetailServiceModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public OrderDetailServiceModel setProduct(ProductServiceModel product) {
        this.product = product;
        return this;
    }

    public OrderDetailServiceModel setQuantity(Short quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderDetailServiceModel setOrderNumber(OrderServiceModel orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }
}
