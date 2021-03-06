package com.project.EStore.model.entity.product;

import com.project.EStore.model.entity.base.BaseEntity;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sizes")
@NoArgsConstructor
@Getter
public class ProductSizeEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ProductSizeEnum size;
    @ManyToMany(mappedBy = "sizes")
    private Set<ProductEntity> products;

    public ProductSizeEntity setSize(ProductSizeEnum size) {
        this.size = size;
        return this;
    }
}
