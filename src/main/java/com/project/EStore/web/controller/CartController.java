package com.project.EStore.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.EStore.exception.CartCookieException;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.view.product.ProductCartViewModel;
import com.project.EStore.service.domain.product.ProductService;
import com.project.EStore.util.validation.ProductCookieValidator;
import com.project.EStore.model.binding.ProductCookieHolderBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("cart")
public class CartController {

    private final ProductService productService;
    private final ProductCookieValidator productCookieValidator;
    private final ModelMapper modelMapper;

    public CartController(ProductService productService, ProductCookieValidator productCookieValidator, ModelMapper modelMapper) {
        this.productService = productService;
        this.productCookieValidator = productCookieValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String getCartView(@CookieValue(name = "cartProducts", required = false) String cartItemsCookie,
                              ObjectMapper objectMapper, Model model) {

        if (cartItemsCookie == null) {
            throw new CartCookieException("Cookie is null");
        }

        ProductCookieHolderBindingModel productCookieHolderBindingModel;
        try {
            productCookieHolderBindingModel = objectMapper.readValue(cartItemsCookie, ProductCookieHolderBindingModel.class);
        } catch (JsonProcessingException e) {
            throw new CartCookieException("Cookie is not valid");
        }

        this.productCookieValidator.validateProductsFromCookie(productCookieHolderBindingModel);

        List<ProductServiceModel> allProductsByIds = this.productService.findAllProductsByIds(productCookieHolderBindingModel
                .getProducts()
                .stream()
                .map(productCookieBindingModel -> Integer.parseInt(productCookieBindingModel.getId()))
                .collect(Collectors.toList()));

        model.addAttribute("products", allProductsByIds.stream()
                .map(productServiceModel -> this.modelMapper.map(productServiceModel, ProductCartViewModel.class))
                .collect(Collectors.toList()));


        return "cart";
    }
}






