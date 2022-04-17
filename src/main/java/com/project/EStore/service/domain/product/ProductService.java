package com.project.EStore.service.domain.product;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;

import java.util.List;

public interface ProductService {

    Integer addProduct(String brand, String model, ProductCategoryEnum category, ProductSizeEnum... sizes);

    ProductServiceModel getProductById(Integer id);

    List<String> getAllBrands();
}
