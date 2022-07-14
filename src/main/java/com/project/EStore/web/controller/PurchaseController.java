package com.project.EStore.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.EStore.exception.CartCookieException;
import com.project.EStore.model.binding.ProductCookieBindingModel;
import com.project.EStore.model.binding.ProductCookieHolderBindingModel;
import com.project.EStore.service.domain.order.OrderService;
import com.project.EStore.util.validation.ProductCookieValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping("products/purchase")
public class PurchaseController {

    private final ProductCookieValidator productCookieValidator;
    private final OrderService orderService;

    public PurchaseController(ProductCookieValidator productCookieValidator, OrderService orderService) {
        this.productCookieValidator = productCookieValidator;
        this.orderService = orderService;
    }

    @PostMapping
    public String postPurchase(@CookieValue(name = "cartProducts", required = false) String cartItemsCookie,
                               ObjectMapper objectMapper, Principal principal) {

        this.productCookieValidator.isCartCookiePresent(cartItemsCookie);

        ProductCookieHolderBindingModel productCookieHolderBindingModel = this.productCookieValidator.mapCookieToPOJO(cartItemsCookie, objectMapper);

        if (productCookieHolderBindingModel.getProducts() == null) {
            throw new CartCookieException("Cookie data is not valid");
        }

        if (productCookieHolderBindingModel.getProducts().isEmpty()) {
            return "redirect:/cart";
        }

        this.productCookieValidator.validateProductsFromCookie(productCookieHolderBindingModel);

        this.orderService.placeOrder(productCookieHolderBindingModel.getProducts().stream()
                .collect(Collectors.toMap(ProductCookieBindingModel::getId, ProductCookieBindingModel::getQuantity)), principal.getName());

        return "redirect:/products/purchase/successful";
    }

    @GetMapping("successful")
    public String getPurchaseView() {
        return "purchaseSuccessful";
    }
}












