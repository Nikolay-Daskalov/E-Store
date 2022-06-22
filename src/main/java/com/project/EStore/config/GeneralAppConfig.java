package com.project.EStore.config;

import com.cloudinary.Cloudinary;
import com.project.EStore.model.service.order.OrderServiceModel;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.view.order.OrderDetailViewModel;
import com.project.EStore.model.view.order.OrderViewModel;
import com.project.EStore.model.view.product.*;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    private static String convertPrice(BigDecimal price) {
        return price.setScale(2, RoundingMode.HALF_UP).toString();
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
                        .setPrice(convertPrice(source.getSupply().getPrice()))
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
                        .setSizes(source.getSizes().stream()
                                .sorted(Comparator.comparingInt(a -> a.getSize().ordinal()))
                                .map(size -> size.getSize().toString())
                                .collect(Collectors.toCollection(LinkedHashSet::new)))
                        .setSupply(new ProductSupplyViewModel()
                                .setPrice(convertPrice(source.getSupply().getPrice()))
                                .setQuantity(source.getSupply().getQuantity()))
                        .setImageUrl(source.getImageUrl())
                        .setType(source.getType().toString());

                return productDetailsViewModel;
            }
        });
        modelMapper.addConverter(new AbstractConverter<ProductServiceModel, ProductCartViewModel>() {
            @Override
            protected ProductCartViewModel convert(ProductServiceModel source) {
                ProductCartViewModel productCartViewModel = new ProductCartViewModel();
                productCartViewModel
                        .setId(source.getId().toString())
                        .setProductPage(String.format("/products/%s/details/%s",
                                source.getCategory().toString().toLowerCase(), source.getId()))
                        .setBrand(source.getBrand())
                        .setModel(source.getModel())
                        .setPrice(convertPrice(source.getSupply().getPrice()))
                        .setImageUrl(source.getImageUrl());

                return productCartViewModel;
            }
        });
        modelMapper.addConverter(new AbstractConverter<OrderServiceModel, OrderViewModel>() {
            @Override
            protected OrderViewModel convert(OrderServiceModel source) {
                List<BigDecimal> totalPrice = new ArrayList<>();
                OrderViewModel orderViewModel = new OrderViewModel();
                orderViewModel
                        .setId(source.getId())
                        .setCreated(source.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                        .setOrderDetails(new HashSet<>(
                                source.getOrderDetails()
                                        .stream()
                                        .map(orderDetailServiceModel -> {
                                            OrderDetailViewModel orderDetailViewModel = new OrderDetailViewModel();
                                            orderDetailViewModel
                                                    .setQuantity(orderDetailServiceModel.getQuantity())
                                                    .setProduct(new ProductOrderViewModel()
                                                            .setBrand(orderDetailServiceModel.getProduct().getBrand())
                                                            .setModel(orderDetailServiceModel.getProduct().getModel())
                                                            .setPrice(convertPrice(orderDetailServiceModel.getProduct().getSupply().getPrice())));

                                            totalPrice.add(orderDetailServiceModel.getProduct().getSupply().getPrice().multiply(BigDecimal.valueOf(orderDetailServiceModel.getQuantity())));
                                            return orderDetailViewModel;
                                        }).collect(Collectors.toSet())
                        ))
                        .setTotalPrice(convertPrice(totalPrice.stream().reduce(new BigDecimal("0"), BigDecimal::add)));

                return orderViewModel;
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
