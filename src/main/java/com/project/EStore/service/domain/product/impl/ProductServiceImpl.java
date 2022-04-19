package com.project.EStore.service.domain.product.impl;

import com.project.EStore.model.entity.product.PictureEntity;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.model.entity.product.ProductSizeEntity;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.order.OrderDetailServiceModel;
import com.project.EStore.model.service.product.PictureServiceModel;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.service.product.ProductSizeServiceModel;
import com.project.EStore.repository.product.ProductRepository;
import com.project.EStore.service.domain.product.ProductService;
import com.project.EStore.service.domain.product.ProductSizeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

        return productEntity == null ? null : this.modelMapper.map(productEntity, ProductServiceModel.class);
    }

    @Override
    public List<String> getAllBrands() {
        return this.productRepository.getAllBrands();
    }

    @Override
    public List<ProductServiceModel> getAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(productEntity -> {
                    PictureEntity pictureEntity = productEntity.getPictures()
                            .stream()
                            .sorted((a, b) -> getFirstPicture(a.getUrl(), b.getUrl()))
                            .findFirst().get();

                    ProductServiceModel mapped = this.modelMapper.map(productEntity, ProductServiceModel.class);
                    mapped.setPictures(new HashSet<>(Set.of(this.modelMapper.map(pictureEntity, PictureServiceModel.class))));

                    return mapped;
                })
                .collect(Collectors.toList());
    }

    private static int getFirstPicture(String firstUrl, String secondUrl) {
        int firstNumberOfPicture = getPictureOrder(firstUrl);
        int secondNumberOfPicture = getPictureOrder(secondUrl);

        return Integer.compare(firstNumberOfPicture, secondNumberOfPicture);
    }

    private static int getPictureOrder(String url) {
        int numberOfPicture = 0;
        for (int i = url.length() - 1; i >= 0; i--) {
            if (url.charAt(i) == '.') {
                numberOfPicture = Integer.parseInt(
                        Character.toString(
                                url.charAt(i - 1)
                        )
                );
                break;
            }
        }

        return numberOfPicture;
    }


    @Override
    public Page<ProductServiceModel> pages() {
        Page<ProductEntity> all = this.productRepository.findAll(PageRequest.of(0, 1));
        Page<ProductServiceModel> map = all.map(productEntity -> this.modelMapper.map(productEntity, ProductServiceModel.class));
        return map;
    }

}
