package com.project.EStore.service.domain.product;

import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.service.domain.init.Init;

import java.math.BigDecimal;
import java.util.Map;

public interface ProductSupplyService extends Init {

    void addSupplyToProduct(BigDecimal price, Short quantity, ProductServiceModel productServiceModel);

    Short getAvailableQuantity(Integer productId);

    void buyByIds(Map<String, String> productsByIdAndCount);
}
