package com.project.EStore.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ProductSupplyServiceModel {

    private ProductServiceModel product;
    private Short quantity;
    private BigDecimal price;
    private LocalDateTime lastUpdatedOn;

    public ProductSupplyServiceModel setProduct(ProductServiceModel product) {
        this.product = product;
        return this;
    }

    public ProductSupplyServiceModel setQuantity(Short quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductSupplyServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductSupplyServiceModel setLastUpdatedOn(LocalDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }
}
