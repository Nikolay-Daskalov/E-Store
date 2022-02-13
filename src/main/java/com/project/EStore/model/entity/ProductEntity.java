package com.project.EStore.model.entity;

import com.project.EStore.model.entity.base.BaseEntity;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    // can add relation to OrderDetailsEnity if i need bidirectional relation
    //TODO: add relation to images

    public ProductEntity() {
        this.sizes = new HashSet<>();
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

    public ProductEntity setSizes(Set<ProductSizeEntity> sizes) {
        this.sizes = sizes;
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
}
