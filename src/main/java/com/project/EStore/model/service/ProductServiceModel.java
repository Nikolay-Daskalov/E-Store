package com.project.EStore.model.service;

import com.project.EStore.model.entity.ProductSupplyEntity;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
public class ProductServiceModel {

    private String brand;
    private String model;
    private ProductCategoryEnum category;
    private Set<ProductSizeServiceModel> sizes;
    private LocalDateTime addedOn;
    private ProductSupplyServiceModel supply;

    public ProductServiceModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductServiceModel setModel(String model) {
        this.model = model;
        return this;
    }

    public ProductServiceModel setCategory(ProductCategoryEnum category) {
        this.category = category;
        return this;
    }

    public ProductServiceModel setSizes(Set<ProductSizeServiceModel> sizes) {
        this.sizes = sizes;
        return this;
    }

    public ProductServiceModel setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
        return this;
    }

    public ProductServiceModel setSupply(ProductSupplyEntity supply) {
        this.supply = supply;
        return this;
    }
}
