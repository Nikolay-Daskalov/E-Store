package com.project.EStore.model.service;

import com.project.EStore.model.entity.enums.ProductSizeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductSizeServiceModel {

    private Integer id;
    private ProductSizeEnum size;

    public ProductSizeServiceModel setSize(ProductSizeEnum size) {
        this.size = size;
        return this;
    }

    public ProductSizeServiceModel setId(Integer id) {
        this.id = id;
        return this;
    }
}
