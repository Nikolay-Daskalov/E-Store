package com.project.EStore.config;

import com.cloudinary.Cloudinary;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.view.product.ProductCardViewModel;
import com.project.EStore.model.view.product.ProductDetailsViewModel;
import com.project.EStore.model.view.product.ProductSupplyViewModel;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GeneralAppConfig extends GlobalMethodSecurityConfiguration implements WebMvcConfigurer {

    private final CloudinaryConfig config;

    public GeneralAppConfig(CloudinaryConfig config) {
        this.config = config;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new AbstractConverter<ProductServiceModel, ProductCardViewModel>() {
            @Override
            protected ProductCardViewModel convert(ProductServiceModel source) {
                ProductCardViewModel productCardViewModel = new ProductCardViewModel();
                productCardViewModel
                        .setId(source.getId())
                        .setBrand(source.getBrand())
                        .setModel(source.getModel())
                        .setPrice(source.getSupply().getPrice().setScale(2, RoundingMode.HALF_UP).toString())
                        .setImageUrl(source.getImageUrl());

                return productCardViewModel;
            }
        });
        modelMapper.addConverter(new AbstractConverter<ProductServiceModel, ProductDetailsViewModel>() {
            @Override
            protected ProductDetailsViewModel convert(ProductServiceModel source) {
                ProductDetailsViewModel productDetailsViewModel = new ProductDetailsViewModel();
                productDetailsViewModel
                        .setId(source.getId())
                        .setBrand(source.getBrand())
                        .setModel(source.getModel())
                        .setSizes(source.getSizes().stream().map(size -> size.getSize().toString()).collect(Collectors.toSet()))
                        .setSupply(new ProductSupplyViewModel()
                                .setPrice(source.getSupply().getPrice().setScale(2, RoundingMode.HALF_UP).toString())
                                .setQuantity(source.getSupply().getQuantity()))
                        .setImageUrl(source.getImageUrl())
                        .setType(source.getType().toString());

                return productDetailsViewModel;
            }
        });
        return modelMapper;
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(Map.of(
                "cloud_name", config.getCloudName(),
                "api_key", config.getApiKey(),
                "api_secret", config.getApiSecret()
        ));
    }

}
