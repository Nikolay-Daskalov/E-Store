package com.project.EStore.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class ProductSupplyBindingModel {

    private ProductBindingModel product;
    private Short quantity;
    private BigDecimal price;

    public ProductSupplyBindingModel setProduct(ProductBindingModel product) {
        this.product = product;
        return this;
    }

    public ProductSupplyBindingModel setQuantity(Short quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductSupplyBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
