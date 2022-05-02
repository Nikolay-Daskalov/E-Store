package com.project.EStore.model.service.product;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.service.order.OrderDetailServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
public class ProductServiceModel {

    private Integer id;
    private String brand;
    private String model;
    private ProductCategoryEnum category;
    private Set<ProductSizeServiceModel> sizes;
    private LocalDateTime addedOn;
    private ProductSupplyServiceModel supply;
    private Set<OrderDetailServiceModel> orderDetails;
    private String imageUrl;
    private ProductTypeEnum type;

    public ProductServiceModel setId(Integer id) {
        this.id = id;
        return this;
    }

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

    public ProductServiceModel setSupply(ProductSupplyServiceModel supply) {
        this.supply = supply;
        return this;
    }

    public ProductServiceModel setOrderDetails(Set<OrderDetailServiceModel> orderDetails) {
        this.orderDetails = orderDetails;
        return this;
    }

    public ProductServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ProductServiceModel setType(ProductTypeEnum type) {
        this.type = type;
        return this;
    }
}
