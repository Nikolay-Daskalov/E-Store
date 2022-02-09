package com.project.EStore.model.entity;

import com.project.EStore.model.entity.base.BaseEntity;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sizes")
@NoArgsConstructor
@Getter
public class SizeEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ProductSizeEnum size;
    //If i need a bidirectional relation with productEntity i need to implement it here

    public SizeEntity setSize(ProductSizeEnum size) {
        this.size = size;
        return this;
    }
}
