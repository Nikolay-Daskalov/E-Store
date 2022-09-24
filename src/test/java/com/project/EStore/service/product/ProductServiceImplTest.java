package com.project.EStore.service.product;

import com.cloudinary.Cloudinary;
import com.project.EStore.exception.ProductNotFoundException;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.repository.product.ProductRepository;
import com.project.EStore.service.product.impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

            if (productEntities.length != allProductsByIds.size()) {
                throw new RuntimeException("Array size must be identical.");
            }

            for (int i = 0; i < allProductsByIds.size(); i++) {
                if (!allProductsByIds.get(i).getBrand().equals(productEntities[i].getBrand())) {
                    throw new RuntimeException("Brands must be identical.");
                }

                if (!allProductsByIds.get(i).getModel().equals(productEntities[i].getModel())) {
                    throw new RuntimeException("Models must be identical.");
                }

                if (!allProductsByIds.get(i).getImageUrl().equals(productEntities[i].getImageUrl())) {
                    throw new RuntimeException("Image URLs must be identical.");
                }

                if (!allProductsByIds.get(i).getCategory().equals(productEntities[i].getCategory())) {
                    throw new RuntimeException("Categories must be identical.");
                }

                if (!allProductsByIds.get(i).getType().equals(productEntities[i].getType())) {
                    throw new RuntimeException("Types must be identical.");
                }

                if (!allProductsByIds.get(i).getId().equals(productEntities[i].getId())) {
                    throw new RuntimeException("Ids must be identical.");
                }
            }
        });
    }

    @Test
    void shouldFindAllByTypeAndCategoryAndPriceBetweenWhenBrandsAreNull() {
        final Collection<String> brandsAreNull = null;
        final Set<String> allBrands = new HashSet<>(Set.of(
                "Brand_1",
                "Brand_2",
                "Brand_3"
        ));
        final Collection<ProductTypeEnum> productTypes = new HashSet<>(Set.of(
                ProductTypeEnum.TOP,
                ProductTypeEnum.BOTTOM
        ));
        final ProductCategoryEnum productCategory = ProductCategoryEnum.RUNNING;
        final ProductEntity[] productEntities = new ProductEntity[]{
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_2")
                        .setModel("Model_2")
                        .setImageUrl("imgUrl_2")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.BOTTOM)
                        .setId(2),
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_3")
                        .setModel("Model_5")
                        .setImageUrl("imgUrl_5")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.TOP)
                        .setId(5)
        };
        final int lowerPrice = 10;
        final int higherPrice = 20;
        final int pageNumber = 0;
        final int pageSize = 2;
        final int totalElements = 2;
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        when(this.productRepositoryMock.findAllBrandsByProductCategory(eq(productCategory))).thenReturn(allBrands);

        when(this.productRepositoryMock.findAllByBrandInAndTypeInAndCategoryAndIsDeletedFalseAndSupply_PriceGreaterThanEqualAndSupply_PriceLessThanEqual(
                same(allBrands), same(productTypes), same(productCategory), eq(new BigDecimal(lowerPrice)), eq(new BigDecimal(higherPrice)), eq(pageRequest)
        )).thenReturn(new PageImpl<>(Arrays.asList(productEntities[0], productEntities[1]), pageRequest, totalElements));

        when(this.modelMapperMock.map(any(ProductEntity.class), same(ProductServiceModel.class))).thenAnswer((invocation -> {
            ProductEntity productEntity = (ProductEntity) invocation.getArguments()[0];
            ProductServiceModel productServiceModel = new ProductServiceModel();
            productServiceModel
                    .setId(productEntity.getId())
                    .setBrand(productEntity.getBrand())
                    .setModel(productEntity.getModel())
                    .setCategory(productEntity.getCategory())
                    .setType(productEntity.getType())
                    .setImageUrl(productEntity.getImageUrl());

            return productServiceModel;
        }));

        Page<ProductServiceModel> result = this.productServiceImpl
                .findAllByBrandAndTypeAndCategoryAndPriceBetween(brandsAreNull, productTypes, productCategory, lowerPrice, higherPrice, pageNumber, pageSize);

        assertNotNull(result);
        assertInstanceOf(PageImpl.class, result);
        assertAll(() -> {
            List<ProductServiceModel> content = result.getContent();

            for (int i = 0 ; i < content.size(); i++){
                if (!content.get(i).getId().equals(productEntities[i].getId())){
                    throw new RuntimeException("Ids should be equal.");
                }
                if (!content.get(i).getBrand().equals(productEntities[i].getBrand())){
                    throw new RuntimeException("Brands should be equal.");
                }
                if (!content.get(i).getModel().equals(productEntities[i].getModel())){
                    throw new RuntimeException("Models should be equal.");
                }
                if (!content.get(i).getCategory().equals(productEntities[i].getCategory())){
                    throw new RuntimeException("Categories should be equal.");
                }
                if (!content.get(i).getType().equals(productEntities[i].getType())){
                    throw new RuntimeException("Types should be equal.");
                }
                if (!content.get(i).getImageUrl().equals(productEntities[i].getImageUrl())){
                    throw new RuntimeException("Image urls should be equal.");
                }
            }
        });
    }

    @Test
    void shouldFindAllByBrandAndTypeAndCategoryAndPriceBetween() {
        final Collection<ProductTypeEnum> productTypes = new HashSet<>(Set.of(
                ProductTypeEnum.TOP,
                ProductTypeEnum.BOTTOM
        ));
        final ProductCategoryEnum productCategory = ProductCategoryEnum.RUNNING;
        final ProductEntity[] productEntities = new ProductEntity[]{
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_2")
                        .setModel("Model_2")
                        .setImageUrl("imgUrl_2")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.BOTTOM)
                        .setId(2),
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_3")
                        .setModel("Model_5")
                        .setImageUrl("imgUrl_5")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.TOP)
                        .setId(5)
        };
        final Set<String> allBrands = new HashSet<>(Set.of(
                "Brand_2",
                "Brand_3"
        ));
        final int lowerPrice = 10;
        final int higherPrice = 20;
        final int pageNumber = 0;
        final int pageSize = 2;
        final int totalElements = 2;
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        when(this.productRepositoryMock.findAllByBrandInAndTypeInAndCategoryAndIsDeletedFalseAndSupply_PriceGreaterThanEqualAndSupply_PriceLessThanEqual(
                same(allBrands), same(productTypes), same(productCategory), eq(new BigDecimal(lowerPrice)), eq(new BigDecimal(higherPrice)), eq(pageRequest)
        )).thenReturn(new PageImpl<>(Arrays.asList(productEntities[0], productEntities[1]), pageRequest, totalElements));

        when(this.modelMapperMock.map(any(ProductEntity.class), same(ProductServiceModel.class))).thenAnswer((invocation -> {
            ProductEntity productEntity = (ProductEntity) invocation.getArguments()[0];
            ProductServiceModel productServiceModel = new ProductServiceModel();
            productServiceModel
                    .setId(productEntity.getId())
                    .setBrand(productEntity.getBrand())
                    .setModel(productEntity.getModel())
                    .setCategory(productEntity.getCategory())
                    .setType(productEntity.getType())
                    .setImageUrl(productEntity.getImageUrl());

            return productServiceModel;
        }));

        Page<ProductServiceModel> result = this.productServiceImpl
                .findAllByBrandAndTypeAndCategoryAndPriceBetween(allBrands, productTypes, productCategory, lowerPrice, higherPrice, pageNumber, pageSize);

        assertNotNull(result);
        assertInstanceOf(PageImpl.class, result);
        assertAll(() -> {
            List<ProductServiceModel> content = result.getContent();

            for (int i = 0 ; i < content.size(); i++){
                if (!content.get(i).getId().equals(productEntities[i].getId())){
                    throw new RuntimeException("Ids should be equal.");
                }
                if (!content.get(i).getBrand().equals(productEntities[i].getBrand())){
                    throw new RuntimeException("Brands should be equal.");
                }
                if (!content.get(i).getModel().equals(productEntities[i].getModel())){
                    throw new RuntimeException("Models should be equal.");
                }
                if (!content.get(i).getCategory().equals(productEntities[i].getCategory())){
                    throw new RuntimeException("Categories should be equal.");
                }
                if (!content.get(i).getType().equals(productEntities[i].getType())){
                    throw new RuntimeException("Types should be equal.");
                }
                if (!content.get(i).getImageUrl().equals(productEntities[i].getImageUrl())){
                    throw new RuntimeException("Image urls should be equal.");
                }
            }
        });
    }

    @Test
    void shouldFindAllByBrandAndCategoryAndPriceBetweenWhenTypesAreNull() {
        final Collection<ProductTypeEnum> productTypesAreNull = null;
        final Collection<ProductTypeEnum> allProductTypes = Arrays.stream(ProductTypeEnum.values()).collect(Collectors.toCollection(() -> EnumSet.allOf(ProductTypeEnum.class)));
        final ProductCategoryEnum productCategory = ProductCategoryEnum.RUNNING;
        final ProductEntity[] productEntities = new ProductEntity[]{
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_2")
                        .setModel("Model_2")
                        .setImageUrl("imgUrl_2")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.BOTTOM)
                        .setId(2),
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_1")
                        .setModel("Model_3")
                        .setImageUrl("imgUrl_3")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.SHOE)
                        .setId(3),
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_2")
                        .setModel("Model_4")
                        .setImageUrl("imgUrl_4")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.ACCESSORIES)
                        .setId(4),
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_3")
                        .setModel("Model_5")
                        .setImageUrl("imgUrl_5")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.TOP)
                        .setId(5)
        };
        final Set<String> allBrands = new HashSet<>(Set.of(
                "Brand_1",
                "Brand_2",
                "Brand_3"
        ));
        final int lowerPrice = 10;
        final int higherPrice = 20;
        final int pageNumber = 0;
        final int pageSize = 2;
        final int totalElements = 2;
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        when(this.productRepositoryMock.findAllByBrandInAndTypeInAndCategoryAndIsDeletedFalseAndSupply_PriceGreaterThanEqualAndSupply_PriceLessThanEqual(
                same(allBrands), eq(allProductTypes), same(productCategory), eq(new BigDecimal(lowerPrice)), eq(new BigDecimal(higherPrice)), eq(pageRequest)
        )).thenReturn(new PageImpl<>(Arrays.asList(productEntities[0], productEntities[1]), pageRequest, totalElements));

        when(this.modelMapperMock.map(any(ProductEntity.class), same(ProductServiceModel.class))).thenAnswer((invocation -> {
            ProductEntity productEntity = (ProductEntity) invocation.getArguments()[0];
            ProductServiceModel productServiceModel = new ProductServiceModel();
            productServiceModel
                    .setId(productEntity.getId())
                    .setBrand(productEntity.getBrand())
                    .setModel(productEntity.getModel())
                    .setCategory(productEntity.getCategory())
                    .setType(productEntity.getType())
                    .setImageUrl(productEntity.getImageUrl());

            return productServiceModel;
        }));

        Page<ProductServiceModel> result = this.productServiceImpl
                .findAllByBrandAndTypeAndCategoryAndPriceBetween(allBrands, productTypesAreNull, productCategory, lowerPrice, higherPrice, pageNumber, pageSize);

        assertNotNull(result);
        assertInstanceOf(PageImpl.class, result);
        assertAll(() -> {
            List<ProductServiceModel> content = result.getContent();

            for (int i = 0 ; i < content.size(); i++){
                if (!content.get(i).getId().equals(productEntities[i].getId())){
                    throw new RuntimeException("Ids should be equal.");
                }
                if (!content.get(i).getBrand().equals(productEntities[i].getBrand())){
                    throw new RuntimeException("Brands should be equal.");
                }
                if (!content.get(i).getModel().equals(productEntities[i].getModel())){
                    throw new RuntimeException("Models should be equal.");
                }
                if (!content.get(i).getCategory().equals(productEntities[i].getCategory())){
                    throw new RuntimeException("Categories should be equal.");
                }
                if (!content.get(i).getType().equals(productEntities[i].getType())){
                    throw new RuntimeException("Types should be equal.");
                }
                if (!content.get(i).getImageUrl().equals(productEntities[i].getImageUrl())){
                    throw new RuntimeException("Image urls should be equal.");
                }
            }
        });
    }

    @Test
    void shouldFindAllByCategoryAndPriceBetweenWhenTypesAndBrandsAreNull() {
        final Collection<String> brandsAreNull = null;
        final Set<String> allBrands = new HashSet<>(Set.of(
                "Brand_1",
                "Brand_2",
                "Brand_3"
        ));
        final Collection<ProductTypeEnum> productTypesAreNull = null;
        final Collection<ProductTypeEnum> allProductTypes = Arrays.stream(ProductTypeEnum.values()).collect(Collectors.toCollection(() -> EnumSet.allOf(ProductTypeEnum.class)));
        final ProductCategoryEnum productCategory = ProductCategoryEnum.RUNNING;
        final ProductEntity[] productEntities = new ProductEntity[]{
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_2")
                        .setModel("Model_2")
                        .setImageUrl("imgUrl_2")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.BOTTOM)
                        .setId(2),
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_1")
                        .setModel("Model_3")
                        .setImageUrl("imgUrl_3")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.SHOE)
                        .setId(3),
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_2")
                        .setModel("Model_4")
                        .setImageUrl("imgUrl_4")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.ACCESSORIES)
                        .setId(4),
                (ProductEntity) new ProductEntity()
                        .setBrand("Brand_3")
                        .setModel("Model_5")
                        .setImageUrl("imgUrl_5")
                        .setCategory(ProductCategoryEnum.RUNNING)
                        .setType(ProductTypeEnum.TOP)
                        .setId(5)
        };
        final int lowerPrice = 10;
        final int higherPrice = 20;
        final int pageNumber = 0;
        final int pageSize = 2;
        final int totalElements = 2;
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        when(this.productRepositoryMock.findAllBrandsByProductCategory(eq(productCategory))).thenReturn(allBrands);

        when(this.productRepositoryMock.findAllByBrandInAndTypeInAndCategoryAndIsDeletedFalseAndSupply_PriceGreaterThanEqualAndSupply_PriceLessThanEqual(
                same(allBrands), eq(allProductTypes), same(productCategory), eq(new BigDecimal(lowerPrice)), eq(new BigDecimal(higherPrice)), eq(pageRequest)
        )).thenReturn(new PageImpl<>(Arrays.asList(productEntities[0], productEntities[1]), pageRequest, totalElements));

        when(this.modelMapperMock.map(any(ProductEntity.class), same(ProductServiceModel.class))).thenAnswer((invocation -> {
            ProductEntity productEntity = (ProductEntity) invocation.getArguments()[0];
            ProductServiceModel productServiceModel = new ProductServiceModel();
            productServiceModel
                    .setId(productEntity.getId())
                    .setBrand(productEntity.getBrand())
                    .setModel(productEntity.getModel())
                    .setCategory(productEntity.getCategory())
                    .setType(productEntity.getType())
                    .setImageUrl(productEntity.getImageUrl());

            return productServiceModel;
        }));

        Page<ProductServiceModel> result = this.productServiceImpl
                .findAllByBrandAndTypeAndCategoryAndPriceBetween(brandsAreNull, productTypesAreNull, productCategory, lowerPrice, higherPrice, pageNumber, pageSize);

        assertNotNull(result);
        assertInstanceOf(PageImpl.class, result);
        assertAll(() -> {
            List<ProductServiceModel> content = result.getContent();

            for (int i = 0 ; i < content.size(); i++){
                if (!content.get(i).getId().equals(productEntities[i].getId())){
                    throw new RuntimeException("Ids should be equal.");
                }
                if (!content.get(i).getBrand().equals(productEntities[i].getBrand())){
                    throw new RuntimeException("Brands should be equal.");
                }
                if (!content.get(i).getModel().equals(productEntities[i].getModel())){
                    throw new RuntimeException("Models should be equal.");
                }
                if (!content.get(i).getCategory().equals(productEntities[i].getCategory())){
                    throw new RuntimeException("Categories should be equal.");
                }
                if (!content.get(i).getType().equals(productEntities[i].getType())){
                    throw new RuntimeException("Types should be equal.");
                }
                if (!content.get(i).getImageUrl().equals(productEntities[i].getImageUrl())){
                    throw new RuntimeException("Image urls should be equal.");
                }
            }
        });
    }
}
