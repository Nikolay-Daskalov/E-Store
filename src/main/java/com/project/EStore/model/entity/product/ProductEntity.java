package com.project.EStore.model.entity.product;

import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.entity.order.OrderDetailEntity;
import com.project.EStore.model.entity.base.BaseEntity;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
public class ProductEntity extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String brand;
    @Column(nullable = false, length = 50)
    private String model;
    @Column(nullable = false, length = 40)
    @Enumerated(EnumType.STRING)
    private ProductCategoryEnum category;
    @ManyToMany
    private Set<ProductSizeEntity> sizes;
    @Column(nullable = false)
    private LocalDateTime addedOn;
    @OneToOne(mappedBy = "product")
    private ProductSupplyEntity supply;
    @OneToMany(mappedBy = "product")
    private Set<OrderDetailEntity> orderDetails;
    @OneToMany(mappedBy = "product")
    private Set<PictureEntity> pictures;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductTypeEnum type;

    public ProductEntity() {
        this.sizes = new HashSet<>();
        this.orderDetails = new HashSet<>();
        this.pictures = new HashSet<>();
    }

    public ProductEntity(String brand, String model, ProductTypeEnum type) {
        this.brand = brand;
        this.model = model;
        this.type = type;
    }

    @PrePersist
    private void initAddedOn() {
        this.addedOn = LocalDateTime.now();
    }


    public ProductEntity setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductEntity setModel(String model) {
        this.model = model;
        return this;
    }

    public ProductEntity setCategory(ProductCategoryEnum category) {
        this.category = category;
        return this;
    }

    public ProductEntity setSupply(ProductSupplyEntity supply) {
        this.supply = supply;
        return this;
    }

    public ProductEntity setType(ProductTypeEnum type) {
        this.type = type;
        return this;
    }
}
