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

        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1644844880/EStore/Fitness/corength-training-25-1.jpg", 1);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1644844894/EStore/Fitness/corength-training-25-2.jpg", 1);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1646457299/EStore/Fitness/domyos-leggings-lady-1.jpg", 2);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1646456700/EStore/Fitness/corength-push-ups-handlebars-1.webp", 3);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1646457096/EStore/Fitness/corength-push-ups-handlebars-2.webp", 3);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1646496614/EStore/Fitness/weider-shaker-300-ml-1.webp", 4);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1646497610/EStore/Fitness/domyos-t-shirt-man-1.webp", 5);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1644937641/EStore/Hiking/quechua-sh100-ultra-warm-1.jpg", 6);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1644937726/EStore/Hiking/quechua-sh100-ultra-warm-2.jpg", 6);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1646991505/EStore/Hiking/forclaz-mt500-man-1.webp", 7);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1645100376/EStore/Hiking/quechua-mh100-man-polar-1.jpg", 8);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1647188644/EStore/Hiking/quechua-nh-arpenaz-100-20-liters-1.webp", 9);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1647315289/EStore/Hiking/forclaz-emergency-help-1.webp", 10);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1647315326/EStore/Hiking/forclaz-emergency-help-2.webp", 10);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1644938838/EStore/Running/kalenji-100-1.jpg", 11);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1644938853/EStore/Running/kalenji-100-2.jpg", 11);
        initPics("https://res.cloudinary.com/dee2hxl5o/image/upload/v1644946260/EStore/Football/kipsta-keepdry-500-1.jpg", 12);

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
