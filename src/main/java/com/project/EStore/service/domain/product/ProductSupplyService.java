package com.project.EStore.service.domain.product;

import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.service.domain.init.Init;

import java.math.BigDecimal;

public interface ProductSupplyService extends Init {

    void addSupplyToProduct(BigDecimal price, Short quantity, ProductServiceModel productServiceModel);
}
