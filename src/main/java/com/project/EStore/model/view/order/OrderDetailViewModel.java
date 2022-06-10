package com.project.EStore.model.view.order;

import com.project.EStore.model.view.product.ProductOrderViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderDetailViewModel {

    private ProductOrderViewModel product;
    private Short quantity;

    public OrderDetailViewModel setProduct(ProductOrderViewModel product) {
        this.product = product;
        return this;
    }

    public OrderDetailViewModel setQuantity(Short quantity) {
        this.quantity = quantity;
        return this;
    }
}
