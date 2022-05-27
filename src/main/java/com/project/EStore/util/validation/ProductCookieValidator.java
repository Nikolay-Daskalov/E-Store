package com.project.EStore.util.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.EStore.exception.CartCookieException;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.binding.ProductCookieBindingModel;
import com.project.EStore.model.binding.ProductCookieHolderBindingModel;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.service.product.ProductSizeServiceModel;
import com.project.EStore.service.domain.product.ProductService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductCookieValidator {

    private final ProductService productService;

    public ProductCookieValidator(ProductService productService) {
        this.productService = productService;
    }

    public void validateProductsFromCookie(ProductCookieHolderBindingModel productCookieHolderBindingModel) {
        ArrayList<ProductCookieBindingModel> products = productCookieHolderBindingModel.getProducts();

        if (products == null){
            throw new CartCookieException("Cookie data is not valid");
        }

        Set<Integer> ids = new HashSet<>();
        for (ProductCookieBindingModel product : products) {
            try {
                Integer id = Integer.parseInt(product.getId());
                Short quantity = Short.parseShort(product.getQuantity());

                if (id < 0) {
                    throw new IllegalArgumentException();
                }

                ProductSizeEnum size = null;
                if (product.getSize() != null) {
                    size = ProductSizeEnum.valueOf(product.getSize());
                }

                ProductServiceModel productById = this.productService.findProductById(id);

                if (productById == null) {
                    throw new IllegalArgumentException();
                }

                if (size == null && productById.getSizes().size() > 0) {
                    throw new IllegalArgumentException();
                }

                if (quantity < 1 || quantity > productById.getSupply().getQuantity()) {
                    throw new IllegalArgumentException();
                }

                Boolean isSizeValid = false;
                for (ProductSizeServiceModel productSize : productById.getSizes()) {
                    if (productSize.getSize().equals(size)) {
                        isSizeValid = true;
                        break;
                    }
                }


                if (!isSizeValid && !productById.getSizes().isEmpty()) {
                    throw new IllegalArgumentException();
                }

                if (ids.contains(id)) {
                    throw new IllegalArgumentException();
                } else {
                    ids.add(id);
                }

            } catch (IllegalArgumentException e) {
                throw new CartCookieException("Cookie data is not valid");
            }
        }
    }

    public void isCartCookiePresent(String cartItemsCookie) {
        if (cartItemsCookie == null) {
            throw new CartCookieException("Cookie is null");
        }
    }

    public ProductCookieHolderBindingModel mapCookieToPOJO(String cartItemsCookie, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(cartItemsCookie, ProductCookieHolderBindingModel.class);
        } catch (JsonProcessingException e) {
            throw new CartCookieException("Cookie is not valid");
        }
    }
}
