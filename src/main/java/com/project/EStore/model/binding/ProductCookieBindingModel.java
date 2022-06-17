package com.project.EStore.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductCookieBindingModel {
    
    private String id;
    private String quantity;
    private String size;

    public ProductCookieBindingModel setId(String id) {
        this.id = id;
        return this;
    }

    public ProductCookieBindingModel setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductCookieBindingModel setSize(String size) {
        this.size = size;
        return this;
    }
}
