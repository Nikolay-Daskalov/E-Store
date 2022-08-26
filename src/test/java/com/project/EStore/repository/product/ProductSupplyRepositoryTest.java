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

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductSupplyRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSupplyRepository productSupplyRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Integer productSupplyId = -1;

    @BeforeEach
    void setUp() {
        BigDecimal price = new BigDecimal("10.00");
        Short quantity = Short.valueOf("10");
        String brand = "ExampleBrand";
        String model = "ExampleModel";

        initSuppliesAndProducts(price, quantity, "someUrl", brand, model, ProductCategoryEnum.FITNESS, ProductTypeEnum.ACCESSORIES);
    }

    @AfterEach
    void tearDown() {
        this.productSupplyRepository.deleteAll();
        this.productRepository.deleteAll();
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

        this.productSupplyId = this.productSupplyRepository.save(productSupplyEntity).getId();

        this.testEntityManager.flush();
        this.testEntityManager.clear();
    }

    @Test
    void shouldDecrementQuantityById() {
        Short decrementWith = Short.valueOf("6");

        this.productSupplyRepository.decrementQuantityById(decrementWith, this.productSupplyId);
        ProductSupplyEntity productSupplyEntity = this.productSupplyRepository.findById(this.productSupplyId).get();

        Short actualQuantity = productSupplyEntity.getQuantity();
        Short expectedQuantity = Short.valueOf("4");

        assertEquals(expectedQuantity, actualQuantity);
    }
}
















