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
import org.springframework.stereotype.Service;

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
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1644844880/EStore/Fitness/corength-training-25.jpg",
                "Corength", "TRAINING 25 KG", ProductCategoryEnum.FITNESS, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("30.90"), Short.valueOf("30"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1646457299/EStore/Fitness/domyos-leggings-lady.jpg",
                "Domyos", "Leggings Lady", ProductCategoryEnum.FITNESS, ProductTypeEnum.BOTTOM, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.XS);

        initSuppliesAndProducts(new BigDecimal("10.90"), Short.valueOf("6"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1646456700/EStore/Fitness/corength-push-ups-handlebars.webp",
                "Corength", "Push ups handlebars", ProductCategoryEnum.FITNESS, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("5.40"), Short.valueOf("10"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1646496614/EStore/Fitness/weider-shaker-300ml.webp",
                "Weider", "Shaker 300 ml", ProductCategoryEnum.FITNESS, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("10.90"), Short.valueOf("22"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1646497610/EStore/Fitness/domyos-t-shirt-man.webp",
                "Domyos", "T-shirt Man", ProductCategoryEnum.FITNESS, ProductTypeEnum.TOP, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("18.40"), Short.valueOf("5"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652693081/EStore/Fitness/corength-ab-wheel.avif",
                "Corength", "AB Wheel", ProductCategoryEnum.FITNESS, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("9.40"), Short.valueOf("8"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652693448/EStore/Fitness/domyos-fitness-bag.avif",
                "Domyos", "Bag", ProductCategoryEnum.FITNESS, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("109"), Short.valueOf("3"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652693695/EStore/Fitness/corength-dumbbell-20kg.avif",
                "Corength", "Dumbbell 20 KG", ProductCategoryEnum.FITNESS, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("10.90"), Short.valueOf("14"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652694038/EStore/Fitness/nyamba-lady-t-shirt-slim-white.avif",
                "Nyamba", "T-shirt Lady Slim", ProductCategoryEnum.FITNESS, ProductTypeEnum.TOP, ProductSizeEnum.XS, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("7.40"), Short.valueOf("4"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652705504/EStore/Fitness/domyos-jr-100-jumping-rope.avif",
                "Domyos", "JR 100 Jumping rope", ProductCategoryEnum.FITNESS, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("79.90"), Short.valueOf("7"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652798687/EStore/Hiking/quechua-mh-100.avif",
                "Quechua", "MH 100", ProductCategoryEnum.HIKING, ProductTypeEnum.SHOE, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("149.00"), Short.valueOf("3"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652798990/EStore/Hiking/forclaz-mw-900.avif",
                "Forclaz", "MW 900 Multiwatch", ProductCategoryEnum.HIKING, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("29.90"), Short.valueOf("8"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652799174/EStore/Hiking/quechua-mh-120.avif",
                "Quechua", "MH 120", ProductCategoryEnum.HIKING, ProductTypeEnum.TOP, ProductSizeEnum.XS, ProductSizeEnum.S, ProductSizeEnum.M);

        initSuppliesAndProducts(new BigDecimal("55.40"), Short.valueOf("25"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1644937641/EStore/Hiking/quechua-sh100-ultra-warm.jpg",
                "Quechua", "SH100 ultra-warm", ProductCategoryEnum.HIKING, ProductTypeEnum.SHOE, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("45.90"), Short.valueOf("5"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652799325/EStore/Hiking/forclaz-travel-100.avif",
                "Forclaz", "TRAVEL 100", ProductCategoryEnum.HIKING, ProductTypeEnum.BOTTOM, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("48.90"), Short.valueOf("8"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1646991505/EStore/Hiking/forclaz-mt500-man.webp",
                "Forclaz", "MT500 Man", ProductCategoryEnum.HIKING, ProductTypeEnum.BOTTOM, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("15.90"), Short.valueOf("18"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1645100376/EStore/Hiking/quechua-mh100-man-polar.jpg",
                "Quechua", "MH100 Polar", ProductCategoryEnum.HIKING, ProductTypeEnum.TOP, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("18.40"), Short.valueOf("5"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1647188644/EStore/Hiking/quechua-nh-arpenaz-100-20-liters.webp",
                "Quechua", "NH ARPENAZ", ProductCategoryEnum.HIKING, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("18.40"), Short.valueOf("9"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1647315289/EStore/Hiking/forclaz-emergency-help.webp",
                "Forclaz", "Emergency Help", ProductCategoryEnum.HIKING, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("36.90"), Short.valueOf("20"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652799682/EStore/Hiking/quechua-mh-140.avif",
                "Quechua", "MH 140", ProductCategoryEnum.HIKING, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("22.90"), Short.valueOf("15"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1644938838/EStore/Running/kalenji-100.jpg",
                "Kalenji", "100", ProductCategoryEnum.RUNNING, ProductTypeEnum.SHOE, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("12.90"), Short.valueOf("22"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1644946260/EStore/Football/kipsta-keepdry-500.jpg",
                "Kipsta", "KEEPDRY 500", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.ACCESSORIES, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);
    }

    private void initSuppliesAndProducts(BigDecimal price, Short quantity, String imageUrl, String brand, String model,
                                         ProductCategoryEnum category, ProductTypeEnum productTypeEnum, ProductSizeEnum... productSizeEnum) {

        ProductSupplyEntity productSupplyEntity = new ProductSupplyEntity();
        productSupplyEntity
                .setPrice(price)
                .setQuantity(quantity);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setBrand(brand)
                .setModel(model)
                .setPictureUrl(imageUrl)
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
    public void addSupplyToProduct(BigDecimal price, Short quantity, ProductServiceModel productServiceModel) {
        ProductEntity productEntity = this.modelMapper.map(productServiceModel, ProductEntity.class);

        ProductSupplyEntity productSupplyEntity = new ProductSupplyEntity();
        productSupplyEntity
                .setPrice(price)
                .setQuantity(quantity)
                .setProduct(productEntity);

        this.productSupplyRepository.save(productSupplyEntity);
    }
}
