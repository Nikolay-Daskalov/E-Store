package com.project.EStore.repository.product;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.model.entity.product.ProductSizeEntity;
import com.project.EStore.model.entity.product.ProductSupplyEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSupplyRepository productSupplyRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        for (ProductSizeEnum size : ProductSizeEnum.values()) {
            this.productSizeRepository.save(new ProductSizeEntity().setSize(size));
        }

        initSuppliesAndProducts(new BigDecimal("14.90"), Short.valueOf("20"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1644844880/EStore/Fitness/corength-training-25.jpg",
                "Corength", "TRAINING 25 KG", ProductCategoryEnum.FITNESS, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("30.90"), Short.valueOf("30"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1646457299/EStore/Fitness/domyos-leggings-lady.jpg",
                "Domyos", "Leggings Woman", ProductCategoryEnum.FITNESS, ProductTypeEnum.BOTTOM,
                ProductSizeEnum.XXS, ProductSizeEnum.XS, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL, ProductSizeEnum.XXL);

        initSuppliesAndProducts(new BigDecimal("10.90"), Short.valueOf("6"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1646456700/EStore/Fitness/corength-push-ups-handlebars.webp",
                "Corength", "Push Ups Handlebars", ProductCategoryEnum.FITNESS, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("79.90"), Short.valueOf("7"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652798687/EStore/Hiking/quechua-mh-100.avif",
                "Quechua", "MH 100 Man", ProductCategoryEnum.HIKING, ProductTypeEnum.SHOE,
                ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL, ProductSizeEnum.XXL);

        initSuppliesAndProducts(new BigDecimal("149.00"), Short.valueOf("3"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652798990/EStore/Hiking/forclaz-mw-900.avif",
                "Forclaz", "MW 900 Multiwatch", ProductCategoryEnum.HIKING, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("29.90"), Short.valueOf("8"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652799174/EStore/Hiking/quechua-mh-120.avif",
                "Quechua", "MH 120 Woman", ProductCategoryEnum.HIKING, ProductTypeEnum.TOP,
                ProductSizeEnum.XS, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("149.00"), Short.valueOf("4"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652868712/EStore/Running/evadict-mt-2-man.avif",
                "Evadict", "MT 2 Man", ProductCategoryEnum.RUNNING, ProductTypeEnum.SHOE,
                ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("27.90"), Short.valueOf("14"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652875332/EStore/Running/kalenji-dry-man.avif",
                "Kalenji", "Dry Man", ProductCategoryEnum.RUNNING, ProductTypeEnum.BOTTOM,
                ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("45.90"), Short.valueOf("11"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652893172/EStore/Running/kiprun-run-900.avif",
                "Kiprun", "Run 900", ProductCategoryEnum.RUNNING, ProductTypeEnum.SHOE,
                ProductSizeEnum.XS, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("23.90"), Short.valueOf("3"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652964460/EStore/Football/tarmak-prevent-500.avif",
                "Tarmak", "Prevent 500", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("18.40"), Short.valueOf("9"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652980363/EStore/Football/kipsta-f500-man.avif",
                "Kipsta", "F500 Man", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.TOP,
                ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("9.40"), Short.valueOf("8"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652963233/EStore/Football/kipsta-f100.avif",
                "Kipsta", "F100", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.BOTTOM,
                ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);
    }

    @AfterEach
    void tearDown() {
        this.productSupplyRepository.deleteAll();
        this.productRepository.deleteAll();
        this.productSizeRepository.deleteAll();
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
                .setImageUrl(imageUrl)
                .setCategory(category)
                .setType(productTypeEnum);

        if (productSizeEnum.length != 0) {
            Arrays.stream(productSizeEnum)
                    .forEach(s -> {
                        ProductSizeEntity sizeEntity = this.productSizeRepository.findBySize(s).get();
                        productEntity.getSizes().add(sizeEntity);
                    });
        }

        productSupplyEntity.setProduct(productEntity);

        this.productSupplyRepository.save(productSupplyEntity);

        this.testEntityManager.flush();
        this.testEntityManager.clear();
    }

    @Test
    void shouldFindAllDistinctBrandsByProductCategory() {
        Set<String> allBrandsByProductCategory = this.productRepository.findAllBrandsByProductCategory(ProductCategoryEnum.FITNESS);

        Set<String> actualReturnedBrands = new HashSet<>();
        for (String brand : allBrandsByProductCategory) {
            assertTrue(() -> actualReturnedBrands.add(brand));
        }
    }

    @Test
    void shouldFindAllByBrandInAndTypeInAndCategoryAndIsDeletedFalseAndSupply_PriceGreaterThanEqualAndSupply_PriceLessThanEqual() {
        List<String> brands = new ArrayList<>(List.of("Kipsta", "Quechua", "Corength"));
        List<ProductTypeEnum> types = new ArrayList<>(List.of(ProductTypeEnum.TOP, ProductTypeEnum.ACCESSORIES));

        ProductCategoryEnum categoryEnum = ProductCategoryEnum.FITNESS;
        BigDecimal highestPrice = new BigDecimal("14.91");
        BigDecimal lowestPrice = new BigDecimal("10.90");

        int pageNumber = 0;
        int pageSize = 3;

        Page<ProductEntity> page = this.productRepository.findAllByBrandInAndTypeInAndCategoryAndIsDeletedFalseAndSupply_PriceGreaterThanEqualAndSupply_PriceLessThanEqual(
                brands, types, categoryEnum, lowestPrice, highestPrice, PageRequest.of(pageNumber, pageSize)
        );

        assertTrue(() -> {
            if (page.getTotalElements() != 2) {
                return false;
            }

            boolean match = page.stream().allMatch(productEntity -> {
                if (!brands.contains(productEntity.getBrand())) {
                    return false;
                }

                if (!types.contains(productEntity.getType())) {
                    return false;
                }

                if (productEntity.getCategory() != categoryEnum) {
                    return false;
                }

                if (productEntity.getSupply().getPrice().compareTo(lowestPrice) < 0) {
                    return false;
                }

                if (productEntity.getSupply().getPrice().compareTo(highestPrice) > 0) {
                    return false;
                }

                return true;
            });

            return match;
        });
    }
}






