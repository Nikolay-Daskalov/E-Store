package com.project.EStore.service.domain.product;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Set;

public interface ProductService {

    Integer HIGHEST_PRICE = 150;

    Integer addProduct(String brand, String model, ProductCategoryEnum category, ProductSizeEnum... sizes);

    ProductServiceModel findProductById(Integer id);
    ProductServiceModel findProductByIdAndType(Integer id, ProductCategoryEnum productCategory);

    Set<String> getAllBrandsByCategory(ProductCategoryEnum productCategory);

    Page<ProductServiceModel> findAllByBrandAndTypeAndCategoryAndPriceBetween(
            Collection<String> brands, Collection<ProductTypeEnum> productTypes, ProductCategoryEnum productCategory,
            Integer lowerPrice, Integer higherPrice, int pageNumber, int pageSize);

}
