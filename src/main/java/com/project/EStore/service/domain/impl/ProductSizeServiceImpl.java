package com.project.EStore.service.domain.impl;

import com.project.EStore.model.entity.ProductSizeEntity;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.ProductSizeServiceModel;
import com.project.EStore.repository.ProductSizeRepository;
import com.project.EStore.service.domain.ProductSizeService;
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
