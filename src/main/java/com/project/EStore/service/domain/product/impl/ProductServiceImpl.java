package com.project.EStore.service.domain.product.impl;

import com.project.EStore.model.entity.base.BaseEntity;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.entity.product.PictureEntity;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.model.entity.product.ProductSizeEntity;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.product.PictureServiceModel;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.service.product.ProductSizeServiceModel;
import com.project.EStore.repository.product.ProductRepository;
import com.project.EStore.service.domain.product.ProductService;
import com.project.EStore.service.domain.product.ProductSizeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    public ProductServiceModel findProductById(Integer id) {
        ProductEntity productEntity = this.productRepository.findById(id).orElse(null);

        return productEntity == null ? null : this.modelMapper.map(productEntity, ProductServiceModel.class);
    }

    @Override
    public Set<String> getAllBrands() {
        return this.productRepository.findAllBrands();
    }

    @Override
    public List<ProductServiceModel> getAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(productEntity -> {
                    PictureEntity pictureEntity = productEntity.getPictures()
                            .stream()
                            .sorted(Comparator.comparingInt(BaseEntity::getId))
                            .findFirst().get();

                    ProductServiceModel mapped = this.modelMapper.map(productEntity, ProductServiceModel.class);
                    mapped.setPictures(new HashSet<>(Set.of(this.modelMapper.map(pictureEntity, PictureServiceModel.class))));

                    return mapped;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductServiceModel> findAllByBrandAndTypeAndCategoryAndPriceBetween(
            Collection<String> brands, Collection<ProductTypeEnum> productTypes, ProductCategoryEnum productCategory,
            BigDecimal lowerPrice, BigDecimal higherPrice, int pageNumber, int pageSize) {

        if (brands == null) {
            //TODO: can you use caching for better performance
            brands = getAllBrands();
        }

        if (productTypes == null) {
            productTypes = Arrays.stream(ProductTypeEnum.values()).collect(Collectors.toSet());
        }

        if (higherPrice == null) {
            //TODO: make it dynamically with cache
            higherPrice = new BigDecimal(Integer.MAX_VALUE);
        }

        Page<ProductEntity> pages = this.productRepository.findAllByBrandInAndTypeInAndCategoryAndSupply_PriceBetween(
                brands, productTypes, productCategory, lowerPrice, higherPrice, PageRequest.of(pageNumber, pageSize)
        );

        return pages.map(productEntity -> {
            PictureEntity pictureEntity = productEntity.getPictures()
                    .stream()
                    .sorted(Comparator.comparingInt(BaseEntity::getId))
                    .findFirst().get();

            ProductServiceModel mapped = this.modelMapper.map(productEntity, ProductServiceModel.class);
            mapped.setPictures(new HashSet<>(Set.of(this.modelMapper.map(pictureEntity, PictureServiceModel.class))));

            return mapped;
        });
    }
}
