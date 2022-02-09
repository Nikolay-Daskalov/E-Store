package com.project.EStore.model.entity;

import com.project.EStore.model.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
public class ProductEntity extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String brand;
    @Column(nullable = false, length = 50)
    private String model;
    @ManyToMany
    private Set<SizeEntity> sizes;
    @Column(nullable = false)
    private LocalDateTime addedOn;
    @OneToOne(mappedBy = "product")
    private ProductSupplyEntity supply;
    //TODO: add relation to images

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

    public ProductEntity setSizes(Set<SizeEntity> sizes) {
        this.sizes = sizes;
        return this;
    }
}
