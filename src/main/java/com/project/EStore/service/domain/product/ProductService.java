package com.project.EStore.service.domain.product;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Integer addProduct(String brand, String model, ProductCategoryEnum category, ProductSizeEnum... sizes);

    ProductServiceModel findProductById(Integer id);

    List<String> getAllBrands();

    List<ProductServiceModel> getAllProducts();

    Page<ProductServiceModel> pages();
}
