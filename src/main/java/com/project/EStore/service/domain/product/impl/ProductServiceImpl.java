package com.project.EStore.service.domain.product.impl;

import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.model.entity.product.ProductSizeEntity;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.service.product.ProductSizeServiceModel;
import com.project.EStore.repository.product.ProductRepository;
import com.project.EStore.service.domain.product.ProductService;
import com.project.EStore.service.domain.product.ProductSizeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
    public Integer addProduct(String brand, String model, ProductCategoryEnum category, ProductSizeEnum... sizes) {
        ProductEntity productEntity = new ProductEntity();
        if (sizes.length != 0) {
            Arrays.stream(sizes).forEach(s -> {
                ProductSizeServiceModel productSizeByName = this.productSizeService.getProductSizeByName(s);
                productEntity.getSizes().add(this.modelMapper.map(productSizeByName, ProductSizeEntity.class));
            });
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

    @Override
    public List<String> getAllBrands() {
        return this.productRepository.getAllBrands();
    }

}
