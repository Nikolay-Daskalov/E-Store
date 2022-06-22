package com.project.EStore.service.domain.product.impl;

import com.project.EStore.model.entity.enums.ProductTypeEnum;
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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public Boolean doesExistById(Integer id) {
        return this.productRepository.existsById(id);
    }

    @Override
    @Transactional
    public void deleteProductById(Integer id) {
        ProductEntity productEntity = this.productRepository.findById(id).get();
        productEntity.setDeleted(true);
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
    public List<ProductServiceModel> findAllProductsByIds(Collection<Integer> ids) {
        List<ProductEntity> products = this.productRepository.findAllByIdIn(ids);
        return products.stream().map(product -> this.modelMapper.map(product, ProductServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductByIdAndCategory(Integer id, ProductCategoryEnum productCategory) {
        ProductEntity productEntity = this.productRepository.findByIdAndCategory(id, productCategory).orElse(null);

        return productEntity == null ? null : this.modelMapper.map(productEntity, ProductServiceModel.class);
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

        Page<ProductEntity> pages = this.productRepository.findAllByBrandInAndTypeInAndCategoryAndIsDeletedAndSupply_PriceGreaterThanEqualAndSupply_PriceLessThanEqual(
                brands, productTypes, productCategory, false, new BigDecimal(lowerPrice), new BigDecimal(higherPrice), PageRequest.of(pageNumber, pageSize)
        );

        return pages.map(productEntity -> this.modelMapper.map(productEntity, ProductServiceModel.class));
    }
}
