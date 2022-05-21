package com.project.EStore.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@Getter
public class ProductCookieHolderBindingModel {

    private ArrayList<ProductCookieBindingModel> products;

    public ArrayList<ProductCookieBindingModel> getProducts() {
        return products;
    }
}
