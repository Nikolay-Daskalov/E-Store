package com.project.EStore.service.domain.product.impl;

import com.project.EStore.model.entity.product.PictureEntity;
import com.project.EStore.model.entity.product.ProductEntity;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.repository.product.PictureRepository;
import com.project.EStore.service.domain.product.PictureService;
import com.project.EStore.service.domain.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public PictureServiceImpl(PictureRepository pictureRepository, ProductService productService, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void init() {
        if (this.pictureRepository.count() > 0) {
            return;
        }

        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1644842693/EStore/Fitness/corength-training-25-1.jpg", 1);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1644844894/EStore/Fitness/corength-training-25-2.jpg", 1);


    }
    private void initPics(String url, Integer id) {
        ProductServiceModel productById = this.productService.getProductById(id);
        ProductEntity mapped = this.modelMapper.map(productById, ProductEntity.class);

        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity
                .setProduct(mapped)
                .setUrl(url);

        this.pictureRepository.save(pictureEntity);
    }
}
