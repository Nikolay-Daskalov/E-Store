package com.project.EStore.service.domain.product.impl;

import com.project.EStore.exception.ProductCriteriaException;
import com.project.EStore.exception.SizeNotFoundException;
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

        addOnInit(ProductSizeEnum.XXS);
        addOnInit(ProductSizeEnum.XS);
        addOnInit(ProductSizeEnum.S);
        addOnInit(ProductSizeEnum.M);
        addOnInit(ProductSizeEnum.L);
        addOnInit(ProductSizeEnum.XL);
        addOnInit(ProductSizeEnum.XXL);
    }

    private void addOnInit(ProductSizeEnum size) {
        ProductSizeEntity productSizeEntity = new ProductSizeEntity().setSize(size);
        this.productSizeRepository.save(productSizeEntity);
    }

    @Override
    public ProductSizeServiceModel getSizeByName(ProductSizeEnum productSizeEnum) {
        ProductSizeEntity productSizeEntity = this.productSizeRepository.findBySize(productSizeEnum).orElseThrow(SizeNotFoundException::new);

        return this.modelMapper.map(productSizeEntity, ProductSizeServiceModel.class);
    }
}
