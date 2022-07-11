package com.project.EStore.model.binding;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.util.validation.annotation.NoSpecialCharacters;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class ProductBindingModel {

    @NotNull
    @NoSpecialCharacters
    @Size(min = 3, max = 35)
    private String brand;
    @NotNull
    @NoSpecialCharacters
    @Size(min = 3, max = 35)
    private String model;
    @NotNull
    private ProductCategoryEnum category;
    private MultipartFile image;
    @NotNull
    private ProductTypeEnum type;
    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "40")
    private Short quantity;
    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "200")
    private BigDecimal price;

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

    public ProductBindingModel setQuantity(Short quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
