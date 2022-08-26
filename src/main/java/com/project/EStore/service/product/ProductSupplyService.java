package com.project.EStore.service.product;

import com.project.EStore.model.service.product.ProductSupplyServiceModel;
import com.project.EStore.service.init.Init;

import java.util.Map;

public interface ProductSupplyService extends Init {

    void addSupplyWithProduct(ProductSupplyServiceModel productSupplyServiceModel);

    void decrementQuantity(Map<String, String> productsByIdAndCount);

    void updateSupplyAndProduct(ProductSupplyServiceModel productSupplyServiceModel);
}
