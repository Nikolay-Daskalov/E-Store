package com.project.EStore.model.view.product;

import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.service.product.ProductSizeServiceModel;
import com.project.EStore.model.service.product.ProductSupplyServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
public class ProductDetailsViewModel {

    private String brand;
    private String model;
    private Set<ProductSizeServiceModel> sizes;
    private ProductSupplyServiceModel supply;
    private String imageUrl;
    private ProductTypeEnum type;

    public ProductDetailsViewModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductDetailsViewModel setModel(String model) {
        this.model = model;
        return this;
    }

    public ProductDetailsViewModel setSizes(Set<ProductSizeServiceModel> sizes) {
        this.sizes = sizes;
        return this;
    }

    public ProductDetailsViewModel setSupply(ProductSupplyServiceModel supply) {
        this.supply = supply;
        return this;
    }

    public ProductDetailsViewModel setPictures(String pictures) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ProductDetailsViewModel setType(ProductTypeEnum type) {
        this.type = type;
        return this;
    }
}
