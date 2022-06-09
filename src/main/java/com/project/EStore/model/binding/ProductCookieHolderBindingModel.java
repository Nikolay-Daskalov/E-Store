package com.project.EStore.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ProductCookieHolderBindingModel {

    private List<ProductCookieBindingModel> products;

    public List<ProductCookieBindingModel> getProducts() {
        return products;
    }
}
