package com.project.EStore.service.domain.product;

import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.product.ProductSizeServiceModel;
import com.project.EStore.service.domain.init.Init;

public interface ProductSizeService extends Init {

    ProductSizeServiceModel getProductSizeByName(ProductSizeEnum productSizeEnum);
}
