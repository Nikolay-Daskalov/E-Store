package com.project.EStore.model.entity.product;

import com.project.EStore.model.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pictures")
@NoArgsConstructor
@Getter
public class PictureEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String url;
    @ManyToOne(optional = false)
    private ProductEntity product;

    public PictureEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public PictureEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }
}
