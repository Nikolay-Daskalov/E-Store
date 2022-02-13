package com.project.EStore.service.domain.impl;

import com.project.EStore.model.entity.ProductEntity;
import com.project.EStore.model.entity.ProductSizeEntity;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.ProductServiceModel;
import com.project.EStore.model.service.ProductSizeServiceModel;
import com.project.EStore.repository.ProductRepository;
import com.project.EStore.service.domain.ProductService;
import com.project.EStore.service.domain.ProductSizeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductSizeService productSizeService;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductSizeService productSizeService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.productSizeService = productSizeService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void init() {
        if (this.productRepository.count() > 0) {
            return;
        }

        addProduct("Corength", "ЛАСТИК TRAINING 25 КГ", ProductCategoryEnum.FITNESS);
    }

    @Override
    public Integer addProduct(String brand, String model, ProductCategoryEnum category, ProductSizeEnum... sizes) {
        ProductEntity productEntity = new ProductEntity();
        if (sizes.length != 0) {
            Set<ProductSizeEntity> sizesEntity = new HashSet<>();
            Arrays.stream(sizes).forEach(s -> {
                ProductSizeServiceModel productSizeByName = this.productSizeService.getProductSizeByName(s);
                sizesEntity.add(this.modelMapper.map(productSizeByName, ProductSizeEntity.class));
            });
            productEntity.setSizes(sizesEntity);
        }

        productEntity
                .setBrand(brand)
                .setModel(model)
                .setCategory(category);

        return this.productRepository.save(productEntity).getId();
    }

    @Override
    public ProductServiceModel getProductById(Integer id) {
        ProductEntity productEntity = this.productRepository.findById(id).orElse(null);

        return productEntity == null ? null : this.modelMapper.map(productEntity, ProductServiceModel.class);
    }


}
