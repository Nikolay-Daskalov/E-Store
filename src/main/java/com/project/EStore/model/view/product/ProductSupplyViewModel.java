package com.project.EStore.model.view.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductSupplyViewModel {

    private Short quantity;
    private String price;

    public ProductSupplyViewModel setQuantity(Short quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductSupplyViewModel setPrice(String price) {
        this.price = price;
        return this;
    }
}
