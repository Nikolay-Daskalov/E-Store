package com.project.EStore.service.domain;

import com.project.EStore.model.service.ProductServiceModel;
import com.project.EStore.service.domain.init.Init;

import java.math.BigDecimal;

public interface ProductSupplyService extends Init {

    void addProductSupply(BigDecimal price, Short quantity, ProductServiceModel productServiceModel);
}
