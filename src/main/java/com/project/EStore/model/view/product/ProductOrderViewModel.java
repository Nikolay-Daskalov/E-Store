package com.project.EStore.model.view.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductOrderViewModel {

    private String productPage;
    private String brand;
    private String model;
    private String price;
    private String imageUrl;

    public ProductOrderViewModel setProductPage(String productPage) {
        this.productPage = productPage;
        return this;
    }

    public ProductOrderViewModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductOrderViewModel setModel(String model) {
        this.model = model;
        return this;
    }

    public ProductOrderViewModel setPrice(String price) {
        this.price = price;
        return this;
    }

    public ProductOrderViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
