package com.project.EStore.service.product;

import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.product.ProductSizeServiceModel;
import com.project.EStore.service.init.Init;

public interface ProductSizeService extends Init {

    ProductSizeServiceModel getSizeByName(ProductSizeEnum productSizeEnum);
}
