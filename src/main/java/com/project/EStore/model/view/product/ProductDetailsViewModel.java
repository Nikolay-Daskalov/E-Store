package com.project.EStore.model.view.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;

@NoArgsConstructor
@Getter
public class ProductDetailsViewModel {

    private Integer id;
    private String brand;
    private String model;
    private LinkedHashSet<String> sizes;
    private ProductSupplyViewModel supply;
    private String imageUrl;
    private String type;

    public ProductDetailsViewModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public ProductDetailsViewModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductDetailsViewModel setModel(String model) {
        this.model = model;
        return this;
    }

    public ProductDetailsViewModel setSizes(LinkedHashSet<String> sizes) {
        this.sizes = sizes;
        return this;
    }

    public ProductDetailsViewModel setSupply(ProductSupplyViewModel supply) {
        this.supply = supply;
        return this;
    }

    public ProductDetailsViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ProductDetailsViewModel setType(String type) {
        this.type = type;
        return this;
    }
}
