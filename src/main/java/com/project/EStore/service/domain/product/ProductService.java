package com.project.EStore.service.domain.product;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

public interface ProductService {

    Integer addProduct(String brand, String model, ProductCategoryEnum category, ProductSizeEnum... sizes);

    ProductServiceModel findProductById(Integer id);

    Set<String> getAllBrandsByCategory(ProductCategoryEnum productCategory);

    Page<ProductServiceModel> findAllByBrandAndTypeAndCategoryAndPriceBetween(
            Collection<String> brands, Collection<ProductTypeEnum> productTypes, ProductCategoryEnum productCategory,
            BigDecimal lowerPrice, BigDecimal higherPrice, int pageNumber, int pageSize);
}
