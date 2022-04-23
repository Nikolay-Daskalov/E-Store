package com.project.EStore.model.view.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductCardViewModel {

    private Integer id;
    private String brand;
    private String model;
    private String price;
    private String imageUrl;

    public ProductCardViewModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public ProductCardViewModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductCardViewModel setModel(String model) {
        this.model = model;
        return this;
    }

    public ProductCardViewModel setPrice(String price) {
        this.price = price;
        return this;
    }

    public ProductCardViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
