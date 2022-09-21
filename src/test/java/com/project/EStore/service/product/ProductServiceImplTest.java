package com.project.EStore.service.product;

import com.cloudinary.Cloudinary;
import com.project.EStore.exception.ProductNotFoundException;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.entity.order.OrderDetailEntity;
import com.project.EStore.model.entity.order.OrderEntity;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.model.entity.product.ProductSizeEntity;
import com.project.EStore.model.entity.product.ProductSupplyEntity;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.service.product.ProductSupplyServiceModel;
import com.project.EStore.repository.product.ProductRepository;
import com.project.EStore.service.product.impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private ProductRepository productRepositoryMock;

    private ModelMapper modelMapperMock;

    private Cloudinary cloudinaryMock;

    private ProductServiceImpl productServiceImpl;

    @BeforeEach
    void setUp() {
        this.productRepositoryMock = mock(ProductRepository.class);
        this.modelMapperMock = mock(ModelMapper.class);
        this.cloudinaryMock = mock(Cloudinary.class, RETURNS_DEEP_STUBS);
        this.productServiceImpl = new ProductServiceImpl(productRepositoryMock, modelMapperMock, cloudinaryMock);
    }

    @AfterEach
    void tearDown() {
        this.productRepositoryMock = null;
        this.modelMapperMock = null;
        this.cloudinaryMock = null;
        this.productServiceImpl = null;
    }

    @Test
    void shouldReturnThatProductExists() {
        final int productId = 1;
        when(this.productRepositoryMock.existsById(productId)).thenReturn(true);

        Boolean exists = this.productServiceImpl.doesExistById(productId);

        assertTrue(exists, "When checking for existing product it should return true.");
    }

    @Test
    void shouldReturnThatProductDoesNotExists() {
        final int productId = 0;
        when(this.productRepositoryMock.existsById(productId)).thenReturn(false);

        Boolean exists = this.productServiceImpl.doesExistById(productId);

        assertFalse(exists, "When checking for non existing product it should return false.");
    }

    @Test
    void shouldThrowExceptionForNonExistingProduct() {
        final int productId = 1;
        when(this.productRepositoryMock.findById(productId)).thenReturn(Optional.empty());

        assertThrowsExactly(NoSuchElementException.class, () -> {
            this.productServiceImpl.deleteProductById(productId);
        });
    }

    @Test
    @DisplayName("shouldSetProductDeletedToTrueAndSetImageURLToN/A()")
    void shouldShallowDeleteTheProduct() throws IOException {
        final String noUrl = "N/A";
        final boolean productIsDeleted = true;
        final int productId = 1;
        final ProductEntity productEntity = new ProductEntity();
        productEntity
                .setDeleted(false)
                .setImageUrl("https://dummy.com/ownderId/image/upload/EStore/SomeCategory/some-product.jpg")
                .setType(ProductTypeEnum.ACCESSORIES)
                .setCategory(ProductCategoryEnum.FITNESS)
                .setBrand("Brand")
                .setModel("Model")
                .setId(productId);
        when(this.productRepositoryMock.findById(productId)).thenReturn(Optional.of(productEntity));
        when(this.cloudinaryMock.uploader().destroy(anyString(), anyMap())).thenReturn(Map.of());

        assertDoesNotThrow(() -> {
            this.productServiceImpl.deleteProductById(productId);
            if (!productEntity.getImageUrl().equals(noUrl)) {
                throw new RuntimeException("The product entity image url should be changed to \"" + noUrl + "\"");
            }
            if (productEntity.getIsDeleted() == false) {
                throw new RuntimeException("The product entity deleted field should be changed to " + productIsDeleted);
            }
        });
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenNoProductIsFound() {
        final int productId = 0;
        when(this.productRepositoryMock.findById(productId)).thenReturn(Optional.empty());

        assertThrowsExactly(ProductNotFoundException.class, () -> {
            this.productServiceImpl.findProductById(productId);
        });
    }

    @Test
    void shouldReturnProductServiceModelWhenFound() {
        final int productId = 1;

        final ProductEntity productEntity = new ProductEntity();
        productEntity
                .setBrand("Brand")
                .setModel("Model")
                .setCategory(ProductCategoryEnum.FITNESS)
                .setType(ProductTypeEnum.ACCESSORIES)
                .setDeleted(false)
                .setImageUrl("someUrl")
                .setId(productId);

        final ProductServiceModel expected = new ProductServiceModel();
        expected
                .setBrand("Brand")
                .setModel("Model")
                .setCategory(ProductCategoryEnum.FITNESS)
                .setType(ProductTypeEnum.ACCESSORIES)
                .setImageUrl("someUrl")
                .setId(productId);

        when(this.modelMapperMock.map(productEntity, ProductServiceModel.class)).thenReturn(expected);
        when(this.productRepositoryMock.findById(productId)).thenReturn(Optional.of(productEntity));

        assertDoesNotThrow(() -> {
            ProductServiceModel actual = this.productServiceImpl.findProductById(productId);

            if (actual != expected) {
                throw new RuntimeException("The product service model should be correctly filled with the correct data.");
            }
        });
    }

    @Test
    void shouldReturnAllProductsByIds() {
        final Collection<Integer> productIds = List.of(
                1, 2, 5
        );

        final ProductEntity[] productEntities = new ProductEntity[]{
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_1")
                        .setModel("Model_1")
                        .setImageUrl("someUrl_1")
                        .setCategory(ProductCategoryEnum.HIKING)
                        .setType(ProductTypeEnum.TOP)
                        .setDeleted(false)
                        .setId(1),
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_2")
                        .setModel("Model_2")
                        .setImageUrl("someUrl_2")
                        .setCategory(ProductCategoryEnum.FITNESS)
                        .setType(ProductTypeEnum.BOTTOM)
                        .setDeleted(false)
                        .setId(2),
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_5")
                        .setModel("Model_5")
                        .setImageUrl("someUrl_5")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.ACCESSORIES)
                        .setDeleted(false)
                        .setId(5),
        };

        when(this.productRepositoryMock.findAllByIdIn(productIds)).thenReturn(Arrays.asList(productEntities));

        for (ProductEntity productEntity : productEntities) {
            when(this.modelMapperMock.map(productEntity, ProductServiceModel.class)).thenAnswer(invocation -> {
                ProductEntity productEntitySource = (ProductEntity) invocation.getArguments()[0];
                ProductServiceModel productServiceModel = new ProductServiceModel();
                productServiceModel
                        .setBrand(productEntitySource.getBrand())
                        .setModel(productEntitySource.getModel())
                        .setImageUrl(productEntitySource.getImageUrl())
                        .setCategory(productEntitySource.getCategory())
                        .setType(productEntitySource.getType())
                        .setId(productEntitySource.getId());

                return productServiceModel;
            });
        }

        assertDoesNotThrow(() -> {
            List<ProductServiceModel> allProductsByIds = this.productServiceImpl.findAllProductsByIds(productIds);

            if (productEntities.length != allProductsByIds.size()){
                throw new RuntimeException("Array size must be identical.");
            }

            for (int i = 0; i < allProductsByIds.size(); i++){
                if (!allProductsByIds.get(i).getBrand().equals(productEntities[i].getBrand())){
                    throw new RuntimeException("Brands must be identical.");
                }

                if (!allProductsByIds.get(i).getModel().equals(productEntities[i].getModel())){
                    throw new RuntimeException("Models must be identical.");
                }

                if (!allProductsByIds.get(i).getImageUrl().equals(productEntities[i].getImageUrl())){
                    throw new RuntimeException("Image URLs must be identical.");
                }

                if (!allProductsByIds.get(i).getCategory().equals(productEntities[i].getCategory())){
                    throw new RuntimeException("Categories must be identical.");
                }

                if (!allProductsByIds.get(i).getType().equals(productEntities[i].getType())){
                    throw new RuntimeException("Types must be identical.");
                }

                if (!allProductsByIds.get(i).getId().equals(productEntities[i].getId())){
                    throw new RuntimeException("Ids must be identical.");
                }
            }
        });
    }
}
