package com.project.EStore.model.view.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductCartViewModel {

    private String productPage;
    private String brand;
    private String model;
    private String price;
    private String imageUrl;
    private String quantity;

    public ProductCartViewModel setProductPage(String productPage) {
        this.productPage = productPage;
        return this;
    }

    public ProductCartViewModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductCartViewModel setModel(String model) {
        this.model = model;
        return this;
    }

    public ProductCartViewModel setPrice(String price) {
        this.price = price;
        return this;
    }

    public ProductCartViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ProductCartViewModel setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }
}
