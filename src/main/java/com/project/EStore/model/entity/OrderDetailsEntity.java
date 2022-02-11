package com.project.EStore.model.entity;

import com.project.EStore.model.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders_details")
@NoArgsConstructor
@Getter
public class OrderDetailsEntity extends BaseEntity {

    @ManyToOne(optional = false)
    private ProductEntity product;
    @Column(nullable = false)
    private Short quantity;
    @ManyToOne(optional = false)
    private OrderEntity orderNumber;

    public OrderDetailsEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public OrderDetailsEntity setQuantity(Short quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderDetailsEntity setOrderNumber(OrderEntity orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }
}
