package com.project.EStore.service.domain;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.ProductServiceModel;
import com.project.EStore.service.domain.init.Init;

public interface ProductService extends Init {

    Integer addProduct(String brand, String model, ProductCategoryEnum category, ProductSizeEnum... sizes);
    ProductServiceModel getProductById(Integer id);
}
