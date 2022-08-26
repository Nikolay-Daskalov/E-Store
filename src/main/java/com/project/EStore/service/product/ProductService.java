package com.project.EStore.service.product;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ProductService {

    Integer HIGHEST_PRICE = 200;

    Boolean doesExistById(Integer id);

    void deleteProductById(Integer id);

    ProductServiceModel findProductById(Integer id);

    List<ProductServiceModel> findAllProductsByIds(Collection<Integer> ids);

    ProductServiceModel findProductByIdAndCategory(Integer id, ProductCategoryEnum productCategory);

    Set<String> getAllBrandsByCategory(ProductCategoryEnum productCategory);

    Page<ProductServiceModel> findAllByBrandAndTypeAndCategoryAndPriceBetween(
            Collection<String> brands, Collection<ProductTypeEnum> productTypes, ProductCategoryEnum productCategory,
            Integer lowerPrice, Integer higherPrice, int pageNumber, int pageSize);
}
