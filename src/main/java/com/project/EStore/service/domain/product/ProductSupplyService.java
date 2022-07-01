package com.project.EStore.service.domain.product;

import com.project.EStore.model.service.product.ProductSupplyServiceModel;
import com.project.EStore.service.domain.init.Init;

import java.util.Map;

public interface ProductSupplyService extends Init {

    void addSupplyWithProduct(ProductSupplyServiceModel productSupplyServiceModel);

    void decrementQuantity(Map<String, String> productsByIdAndCount);

    void replaceSupplyWithProduct(ProductSupplyServiceModel productSupplyServiceModel);
}
