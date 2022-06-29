package com.project.EStore.model.view.product;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@Getter
public class ProductEditViewModel {

    private Integer id;
    private String brand;
    private String model;
    private ProductCategoryEnum category;
    private String imageUrl;
    private ProductTypeEnum type;
    private Short quantity;
    private BigDecimal price;
    private Set<ProductSizeEnum> sizes;

    public ProductEditViewModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public ProductEditViewModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductEditViewModel setModel(String model) {
        this.model = model;
        return this;
    }

    public ProductEditViewModel setCategory(ProductCategoryEnum category) {
        this.category = category;
        return this;
    }

    public ProductEditViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ProductEditViewModel setType(ProductTypeEnum type) {
        this.type = type;
        return this;
    }

    public ProductEditViewModel setQuantity(Short quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductEditViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductEditViewModel setSizes(Set<ProductSizeEnum> sizes) {
        this.sizes = sizes;
        return this;
    }
}
