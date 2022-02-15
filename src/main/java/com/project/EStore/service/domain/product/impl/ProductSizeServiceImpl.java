package com.project.EStore.service.domain.product.impl;

import com.project.EStore.model.entity.product.ProductSizeEntity;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.product.ProductSizeServiceModel;
import com.project.EStore.repository.product.ProductSizeRepository;
import com.project.EStore.service.domain.product.ProductSizeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductSizeServiceImpl implements ProductSizeService {

    private final ProductSizeRepository productSizeRepository;
    private final ModelMapper modelMapper;

    public ProductSizeServiceImpl(ProductSizeRepository productSizeRepository, ModelMapper modelMapper) {
        this.productSizeRepository = productSizeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void init() {
        if (this.productSizeRepository.count() > 0) {
            return;
        }

        addOnInit(ProductSizeEnum.XS);
        addOnInit(ProductSizeEnum.S);
        addOnInit(ProductSizeEnum.M);
        addOnInit(ProductSizeEnum.L);
        addOnInit(ProductSizeEnum.XL);
        addOnInit(ProductSizeEnum.SHOE_35);
        addOnInit(ProductSizeEnum.SHOE_36);
        addOnInit(ProductSizeEnum.SHOE_37);
        addOnInit(ProductSizeEnum.SHOE_38);
        addOnInit(ProductSizeEnum.SHOE_39);
        addOnInit(ProductSizeEnum.SHOE_40);
        addOnInit(ProductSizeEnum.SHOE_41);
        addOnInit(ProductSizeEnum.SHOE_42);
        addOnInit(ProductSizeEnum.SHOE_43);
        addOnInit(ProductSizeEnum.SHOE_44);
        addOnInit(ProductSizeEnum.SHOE_45);
        addOnInit(ProductSizeEnum.SHOE_46);
        addOnInit(ProductSizeEnum.SHOE_47);
    }

    private void addOnInit(ProductSizeEnum size) {
        ProductSizeEntity productSizeEntity = new ProductSizeEntity().setSize(size);
        this.productSizeRepository.save(productSizeEntity);
    }

    @Override
    public ProductSizeServiceModel getProductSizeByName(ProductSizeEnum productSizeEnum) {
        ProductSizeEntity productSizeEntity = this.productSizeRepository.findBySize(productSizeEnum).orElse(null);

        return productSizeEntity == null ? null : this.modelMapper.map(productSizeEntity, ProductSizeServiceModel.class);
    }
}
