package com.project.EStore.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.EStore.model.binding.ProductCookieHolderBindingModel;
import com.project.EStore.util.validation.ProductCookieValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("products/purchase")
public class PurchaseController {

    private final ProductCookieValidator productCookieValidator;

    public PurchaseController(ProductCookieValidator productCookieValidator) {
        this.productCookieValidator = productCookieValidator;
    }

    @GetMapping
    public String getPurchaseView(@CookieValue(name = "cartProducts", required = false) String cartItemsCookie,
                                  ObjectMapper objectMapper, Model model) {

        this.productCookieValidator.isCartCookieValid(cartItemsCookie);

        ProductCookieHolderBindingModel productCookieHolderBindingModel = this.productCookieValidator.mapCookieToPOJO(cartItemsCookie, objectMapper);

        this.productCookieValidator.validateProductsFromCookie(productCookieHolderBindingModel);

        return "purchase";
    }
}
