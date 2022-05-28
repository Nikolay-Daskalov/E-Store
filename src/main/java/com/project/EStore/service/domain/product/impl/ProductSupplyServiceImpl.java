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

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

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
                "Domyos", "Leggings Woman", ProductCategoryEnum.FITNESS, ProductTypeEnum.BOTTOM, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.XS);

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
                "Quechua", "MH 100 Man", ProductCategoryEnum.HIKING, ProductTypeEnum.SHOE, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("149.00"), Short.valueOf("3"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652798990/EStore/Hiking/forclaz-mw-900.avif",
                "Forclaz", "MW 900 Multiwatch", ProductCategoryEnum.HIKING, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("29.90"), Short.valueOf("8"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652799174/EStore/Hiking/quechua-mh-120.avif",
                "Quechua", "MH 120 Woman", ProductCategoryEnum.HIKING, ProductTypeEnum.TOP, ProductSizeEnum.XS, ProductSizeEnum.S, ProductSizeEnum.M);

        initSuppliesAndProducts(new BigDecimal("55.40"), Short.valueOf("25"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1644937641/EStore/Hiking/quechua-sh100-ultra-warm.jpg",
                "Quechua", "SH100 Ultra-Warm Man", ProductCategoryEnum.HIKING, ProductTypeEnum.SHOE, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("45.90"), Short.valueOf("5"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652799325/EStore/Hiking/forclaz-travel-100.avif",
                "Forclaz", "TRAVEL 100 Man", ProductCategoryEnum.HIKING, ProductTypeEnum.BOTTOM, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("48.90"), Short.valueOf("8"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1646991505/EStore/Hiking/forclaz-mt500-man.webp",
                "Forclaz", "MT500 Man", ProductCategoryEnum.HIKING, ProductTypeEnum.BOTTOM, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("15.90"), Short.valueOf("18"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1645100376/EStore/Hiking/quechua-mh100-man-polar.jpg",
                "Quechua", "MH100 Polar Man", ProductCategoryEnum.HIKING, ProductTypeEnum.TOP, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

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
                "Kalenji", "100 Man", ProductCategoryEnum.RUNNING, ProductTypeEnum.SHOE, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("149.00"), Short.valueOf("4"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652868712/EStore/Running/evadict-mt-2-man.avif",
                "Evadict", "MT 2 Man", ProductCategoryEnum.RUNNING, ProductTypeEnum.SHOE, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("27.90"), Short.valueOf("14"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652875332/EStore/Running/kalenji-dry-man.avif",
                "Kalenji", "Dry Man", ProductCategoryEnum.RUNNING, ProductTypeEnum.BOTTOM, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("139.00"), Short.valueOf("6"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652873008/EStore/Running/asics-gel-windhawk-woman.avif",
                "Asics", "Gel Winhawk Woman", ProductCategoryEnum.RUNNING, ProductTypeEnum.SHOE, ProductSizeEnum.XS, ProductSizeEnum.S, ProductSizeEnum.M);

        initSuppliesAndProducts(new BigDecimal("21.90"), Short.valueOf("22"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652876044/EStore/Running/kalenji-soft-bottle.avif",
                "Kalenji", "Soft water battle", ProductCategoryEnum.RUNNING, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("45.90"), Short.valueOf("5"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652878800/EStore/Running/evadict-belt-light.avif",
                "Evadict", "Belt", ProductCategoryEnum.RUNNING, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("45.90"), Short.valueOf("11"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652893172/EStore/Running/kiprun-run-900.avif",
                "Kiprun", "Run 900", ProductCategoryEnum.RUNNING, ProductTypeEnum.SHOE, ProductSizeEnum.XS, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("54.90"), Short.valueOf("10"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652893383/EStore/Running/kalenji-onstart-710.avif",
                "Kalenji", "Onstart 710", ProductCategoryEnum.RUNNING, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("18.40"), Short.valueOf("7"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652893520/EStore/Running/kalenji-cap-run.avif",
                "Kalenji", "Cap Rainproof", ProductCategoryEnum.RUNNING, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("14.90"), Short.valueOf("6"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652893746/EStore/Running/kalenji-waist-bag-smartphone.avif",
                "Kalenji", "Waist Bag Smartphone", ProductCategoryEnum.RUNNING, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("12.90"), Short.valueOf("22"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1644946260/EStore/Football/kipsta-keepdry-500.jpg",
                "Kipsta", "KEEPDRY 500", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.ACCESSORIES, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("19.90"), Short.valueOf("16"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652962476/EStore/Football/kipsta-f500-hybride.avif",
                "Kipsta", "F500 Hybride", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("184.00"), Short.valueOf("5"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652962647/EStore/Football/adidas-mundial-team-tf.avif",
                "Adidas", "Mundial Team Tf", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.SHOE, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);

        initSuppliesAndProducts(new BigDecimal("11.40"), Short.valueOf("9"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652962816/EStore/Football/kipsta-essential-backpack.avif",
                "Kipsta", "Essential BackPack", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("34.90"), Short.valueOf("7"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652962946/EStore/Football/kipsta-jacket-t100.avif",
                "Kipsta", "T100 Rainproof", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.TOP, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("9.40"), Short.valueOf("8"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652963233/EStore/Football/kipsta-f100.avif",
                "Kipsta", "F100", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.BOTTOM, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("64.90"), Short.valueOf("7"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652963381/EStore/Football/adidas-champions-league-ball.avif",
                "Adidas", "Champions League 2022", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("43.90"), Short.valueOf("6"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652963887/EStore/Football/kipsta-agility-500-sg.avif",
                "Kipsta", "Agility 500 SG", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.SHOE, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L);

        initSuppliesAndProducts(new BigDecimal("23.90"), Short.valueOf("3"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652964460/EStore/Football/tarmak-prevent-500.avif",
                "Tarmak", "Prevent 500", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.ACCESSORIES);

        initSuppliesAndProducts(new BigDecimal("18.40"), Short.valueOf("9"),
                "https://res.cloudinary.com/dee2hxl5o/image/upload/v1652980363/EStore/Football/kipsta-f500-man.avif",
                "Kipsta", "F500 Man", ProductCategoryEnum.FOOTBALL, ProductTypeEnum.TOP, ProductSizeEnum.S, ProductSizeEnum.M, ProductSizeEnum.L, ProductSizeEnum.XL);
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

    @Override
    public Short getAvailableQuantity(Integer productId) {
        return this.productSupplyRepository.findQuantityByProduct_Id(productId);
    }

    @Override
    @Transactional
    public void decrementQuantity(Map<String, String> productsByIdAndCount) {
        for (Map.Entry<String, String> entry : productsByIdAndCount.entrySet()) {
            this.productSupplyRepository.decrementQuantityById(Short.valueOf(entry.getValue()), Integer.parseInt(entry.getKey()));
        }
    }
}
