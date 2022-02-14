package com.project.EStore.model.service.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PictureServiceModel {

    private Integer id;
    private String url;
    private ProductServiceModel product;

    public PictureServiceModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public PictureServiceModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public PictureServiceModel setProduct(ProductServiceModel product) {
        this.product = product;
        return this;
    }
}
