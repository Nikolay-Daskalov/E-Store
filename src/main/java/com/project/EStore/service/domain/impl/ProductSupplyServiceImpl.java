package com.project.EStore.service.domain.impl;

import com.project.EStore.model.entity.ProductEntity;
import com.project.EStore.model.entity.ProductSupplyEntity;
import com.project.EStore.model.service.ProductServiceModel;
import com.project.EStore.repository.ProductSupplyRepository;
import com.project.EStore.service.domain.ProductService;
import com.project.EStore.service.domain.ProductSupplyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductSupplyServiceImpl implements ProductSupplyService {

    private final ProductSupplyRepository productSupplyRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductSupplyServiceImpl(ProductSupplyRepository productSupplyRepository, ProductService productService, ModelMapper modelMapper) {
        this.productSupplyRepository = productSupplyRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void init() {
        if (this.productSupplyRepository.count() > 0) {
            return;
        }


    }

    @Override
    public void addProductSupply(BigDecimal price, Short quantity, ProductServiceModel productServiceModel) {
        ProductEntity productEntity = this.modelMapper.map(productServiceModel, ProductEntity.class);

        ProductSupplyEntity productSupplyEntity = new ProductSupplyEntity();
        productSupplyEntity
                .setPrice(price)
                .setQuantity(quantity)
                .setProduct(productEntity);

        this.productSupplyRepository.save(productSupplyEntity);
    }
}
