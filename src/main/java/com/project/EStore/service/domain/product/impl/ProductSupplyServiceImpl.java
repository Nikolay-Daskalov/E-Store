package com.project.EStore.service.domain.product.impl;

import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.model.entity.product.ProductSizeEntity;
import com.project.EStore.model.entity.product.ProductSupplyEntity;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.service.product.ProductSizeServiceModel;
import com.project.EStore.repository.product.ProductSupplyRepository;
import com.project.EStore.service.domain.product.ProductService;
import com.project.EStore.service.domain.product.ProductSizeService;
import com.project.EStore.service.domain.product.ProductSupplyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class ProductSupplyServiceImpl implements ProductSupplyService {

    private final ProductSupplyRepository productSupplyRepository;
    private final ProductService productService;
    private final ProductSizeService productSizeService;
    private final ModelMapper modelMapper;

    public ProductSupplyServiceImpl(ProductSupplyRepository productSupplyRepository, ProductService productService, ProductSizeService productSizeService, ModelMapper modelMapper) {
        this.productSupplyRepository = productSupplyRepository;
        this.productService = productService;
        this.productSizeService = productSizeService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void init() {
        if (this.productSupplyRepository.count() > 0) {
            return;
        }

        initSuppliesAndProducts(new BigDecimal("14.90"), Short.valueOf("20"),
                "Corength", "Rubber Band TRAINING 25 КГ", ProductCategoryEnum.FITNESS, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("55.40"), Short.valueOf("25"),
                "Quechua", "SH100 ultra-warm", ProductCategoryEnum.HIKING, ProductTypeEnum.SHOE, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("22.90"), Short.valueOf("15"),
                "Kalenji", "100", ProductCategoryEnum.RUNNING, ProductTypeEnum.SHOE, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("12.90"), Short.valueOf("22"),
                "Kipsta", "KEEPDRY 500", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.ACCESSORIES, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);
    }

    private void initSuppliesAndProducts(BigDecimal price, Short quantity, String brand, String model,
                                         ProductCategoryEnum category, ProductTypeEnum productTypeEnum, ProductSizeEnum... productSizeEnum) {

        ProductSupplyEntity productSupplyEntity = new ProductSupplyEntity();
        productSupplyEntity
                .setPrice(price)
                .setQuantity(quantity);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setBrand(brand)
                .setModel(model)
                .setCategory(category)
                .setType(productTypeEnum);

        if (productSizeEnum.length != 0) {
            Arrays.stream(productSizeEnum)
                    .forEach(s -> {
                        ProductSizeServiceModel productSizeByName = this.productSizeService.getProductSizeByName(s);
                        ProductSizeEntity productSizeEntity = this.modelMapper.map(productSizeByName, ProductSizeEntity.class);
                        productEntity.getSizes().add(productSizeEntity);
                    });
        }

        productSupplyEntity.setProduct(productEntity);

        this.productSupplyRepository.save(productSupplyEntity);
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
