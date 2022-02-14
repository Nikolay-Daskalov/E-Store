package com.project.EStore.model.entity.order;

import com.project.EStore.model.entity.base.BaseEntity;
import com.project.EStore.model.entity.product.ProductEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders_details")
@NoArgsConstructor
@Getter
public class OrderDetailEntity extends BaseEntity {

    @ManyToOne(optional = false)
    private ProductEntity product;
    @Column(nullable = false)
    private Short quantity;
    @ManyToOne(optional = false)
    private OrderEntity orderNumber;

    public OrderDetailEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public OrderDetailEntity setQuantity(Short quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderDetailEntity setOrderNumber(OrderEntity orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }
}
