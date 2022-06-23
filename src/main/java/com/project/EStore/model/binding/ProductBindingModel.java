package com.project.EStore.model.binding;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
public class ProductBindingModel {

    private String brand;
    private String model;
    private ProductCategoryEnum category;
    private MultipartFile image;
    private ProductTypeEnum type;

    public ProductBindingModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductBindingModel setModel(String model) {
        this.model = model;
        return this;
    }

    public ProductBindingModel setCategory(ProductCategoryEnum category) {
        this.category = category;
        return this;
    }

    public ProductBindingModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public ProductBindingModel setType(ProductTypeEnum type) {
        this.type = type;
        return this;
    }
}
