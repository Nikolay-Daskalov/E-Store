package com.project.EStore.service.product.impl;

import com.cloudinary.Cloudinary;
import com.project.EStore.exception.ProductNotFoundException;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.repository.product.ProductRepository;
import com.project.EStore.service.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final Cloudinary cloudinary;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, Cloudinary cloudinary) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.cloudinary = cloudinary;
    }

    @Override
    public Boolean doesExistById(Integer id) {
        return this.productRepository.existsById(id);
    }

    @Override
    @Transactional
    public void deleteProductById(Integer id) {
        ProductEntity productEntity = this.productRepository.findById(id).get();
        String publicId = this.getPublicId(productEntity.getImageUrl());

        try {
            this.cloudinary.uploader().destroy(publicId, Map.of(
                    "resource_type", "image"
            ));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        productEntity.setImageUrl("N/A");
        productEntity.setDeleted(true);
    }

    private String getPublicId(String imageUrl) {
        String publicIdWithExtension = imageUrl.split("/EStore")[1];
        String publicId = publicIdWithExtension.split("\\.")[0];

        return "EStore" + publicId;
    }

    @Override
    public ProductServiceModel findProductById(Integer id) {
        ProductEntity productEntity = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        return this.modelMapper.map(productEntity, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllProductsByIds(Collection<Integer> ids) {
        List<ProductEntity> products = this.productRepository.findAllByIdIn(ids);
        return products.stream().map(product -> this.modelMapper.map(product, ProductServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductByIdAndCategory(Integer id, ProductCategoryEnum productCategory) {
        ProductEntity productEntity = this.productRepository.findByIdAndCategory(id, productCategory).orElseThrow(ProductNotFoundException::new);

        return this.modelMapper.map(productEntity, ProductServiceModel.class);
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

        Page<ProductEntity> pages = this.productRepository.findAllByBrandInAndTypeInAndCategoryAndIsDeletedFalseAndSupply_PriceGreaterThanEqualAndSupply_PriceLessThanEqual(
                brands, productTypes, productCategory, new BigDecimal(lowerPrice), new BigDecimal(higherPrice), PageRequest.of(pageNumber, pageSize)
        );

        return pages.map(productEntity -> this.modelMapper.map(productEntity, ProductServiceModel.class));
    }
}
