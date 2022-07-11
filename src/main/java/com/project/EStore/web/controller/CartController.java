package com.project.EStore.web.controller;

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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        this.productCookieValidator.isCartCookiePresent(cartItemsCookie);

        ProductCookieHolderBindingModel productCookieHolderBindingModel = this.productCookieValidator.mapCookieToPOJO(cartItemsCookie, objectMapper);

        if (productCookieHolderBindingModel.getProducts() == null){
            throw new CartCookieException("Cookie data is not valid");
        }

        if (productCookieHolderBindingModel.getProducts().isEmpty()) {
            return "cart";
        }

        this.productCookieValidator.validateProductsFromCookie(productCookieHolderBindingModel);

        Map<String, String> productQuantity = new HashMap<>();
        List<ProductServiceModel> allProductsByIds = this.productService.findAllProductsByIds(productCookieHolderBindingModel
                .getProducts()
                .stream()
                .peek(productCookieBindingModel -> productQuantity.put(productCookieBindingModel.getId(), productCookieBindingModel.getQuantity()))
                .map(productCookieBindingModel -> Integer.parseInt(productCookieBindingModel.getId()))
                .collect(Collectors.toList()));

        model.addAttribute("products", allProductsByIds.stream()
                .map(productServiceModel ->
                        this.modelMapper.map(productServiceModel, ProductCartViewModel.class).setQuantity(productQuantity.get(productServiceModel.getId().toString())))
                .collect(Collectors.toList()));

        model.addAttribute("totalPrice", allProductsByIds.stream()
                .map(product -> product.getSupply().getPrice().multiply(new BigDecimal(productQuantity.get(product.getId().toString()))))
                .reduce(new BigDecimal("0"), BigDecimal::add).setScale(2, RoundingMode.HALF_UP).toString());

        return "cart";
    }
}