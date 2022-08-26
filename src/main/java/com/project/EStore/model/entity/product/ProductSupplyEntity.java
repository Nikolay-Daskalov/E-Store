package com.project.EStore.model.entity.product;

import com.project.EStore.model.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products_supplies")
@NoArgsConstructor
@Getter
public class ProductSupplyEntity extends BaseEntity {

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private ProductEntity product;
    @Column(nullable = false)
    private Short quantity;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private LocalDateTime lastUpdatedOn;

    @PrePersist
    @PreUpdate
    private void initLastUpdatedOn(){
        this.lastUpdatedOn = LocalDateTime.now();
    }

    public ProductSupplyEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public ProductSupplyEntity setQuantity(Short quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductSupplyEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
