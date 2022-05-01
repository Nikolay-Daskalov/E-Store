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

        return productEntity == null ? null : this.mapProductEntityWithPicturesSorted(productEntity, false);
    }

    @Override
    public Set<String> getAllBrandsByCategory(ProductCategoryEnum productCategory) {
        return this.productRepository.findAllBrandsByProductCategory(productCategory);
    }

    @Override
    public Page<ProductServiceModel> findAllByBrandAndTypeAndCategoryAndPriceBetween(
            Collection<String> brands, Collection<ProductTypeEnum> productTypes, ProductCategoryEnum productCategory,
            Integer lowerPrice, Integer higherPrice, int pageNumber, int pageSize) {

        if (brands == null) {
            brands = getAllBrandsByCategory(productCategory);
        }

        if (productTypes == null) {
            productTypes = Arrays.stream(ProductTypeEnum.values()).collect(Collectors.toSet());
        }

        Page<ProductEntity> pages = this.productRepository.findAllByBrandInAndTypeInAndCategoryAndSupply_PriceGreaterThanEqualAndSupply_PriceLessThanEqual(
                brands, productTypes, productCategory, new BigDecimal(lowerPrice), new BigDecimal(higherPrice), PageRequest.of(pageNumber, pageSize)
        );

        return pages.map(productEntity -> this.mapProductEntityWithPicturesSorted(productEntity, true));
    }

    private ProductServiceModel mapProductEntityWithPicturesSorted(ProductEntity productEntity, boolean onlyFirst) {
        ProductServiceModel mapped = this.modelMapper.map(productEntity, ProductServiceModel.class);
        LinkedHashSet<PictureServiceModel> pictureServiceModels = new LinkedHashSet<>();

        if (onlyFirst) {
            PictureEntity pictureEntity = productEntity.getPictures().stream().sorted(Comparator.comparingInt(BaseEntity::getId)).findFirst().get();
            pictureServiceModels.add(this.modelMapper.map(pictureEntity, PictureServiceModel.class));
        } else {
            LinkedHashSet<PictureServiceModel> allPicturesSorted = productEntity.getPictures().stream()
                    .sorted(Comparator.comparingInt(BaseEntity::getId)).map(picture -> this.modelMapper.map(picture, PictureServiceModel.class))
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            pictureServiceModels.addAll(allPicturesSorted);
        }

        mapped.setPictures(pictureServiceModels);

        return mapped;
    }
}
