package com.project.EStore.util.validation;

import com.project.EStore.exception.ProductCriteriaException;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.util.validation.annotation.validator.NoSpecialCharactersValidator;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.EStore.service.domain.product.ProductService.HIGHEST_PRICE;

@Component
public class ProductValidator {

    private final NoSpecialCharactersValidator noSpecialCharactersValidator;

    public ProductValidator(NoSpecialCharactersValidator noSpecialCharactersValidator) {
        this.noSpecialCharactersValidator = noSpecialCharactersValidator;
    }

    public boolean isIdValid(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isCategoryValid(String category) {
        try {
            if (category.equals(category.toLowerCase())) {
                ProductCategoryEnum.valueOf(category.toUpperCase());
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void isPriceAndPageValid(String lowestPrice, String highestPrice, String pageNumber) {
        try {
            int lowestPriceConverted = Integer.parseInt(lowestPrice);
            int highestPriceConverted = Integer.parseInt(highestPrice);
            int pageNumberConverted = Integer.parseInt(pageNumber);

            if (lowestPriceConverted < 0 || lowestPriceConverted > HIGHEST_PRICE || lowestPriceConverted % 5 != 0) {
                throw new ProductCriteriaException("Lowest price limit exceeded");
            }

            if (highestPriceConverted > HIGHEST_PRICE || highestPriceConverted < 0 || highestPriceConverted % 5 != 0) {
                throw new ProductCriteriaException("Highest price limit exceeded");
            }

            if (pageNumberConverted < 0) {
                throw new ProductCriteriaException("Page number limit exceeded");
            }
        } catch (NumberFormatException e) {
            throw new ProductCriteriaException("Price or page not valid");
        }
    }

    public Set<String> addBrandsToCheck(Set<String> brands, Set<String> brandCheckboxesToCheck, Model model) {
        if (brands != null) {
            if (brands.isEmpty()) {
                return null;
            } else {
                boolean isValid = brands.stream().allMatch(brand -> this.noSpecialCharactersValidator.isValid(brand, null));
                if (!isValid) {
                    throw new ProductCriteriaException("Brands contain special characters");
                }
                brandCheckboxesToCheck.addAll(brands);
                model.addAttribute("brands", String.join(",", brands));
                return new LinkedHashSet<>(brands);
            }
        }

        return null;
    }

    public Set<ProductTypeEnum> addTypesToCheck(Set<String> types, Set<String> typeCheckboxesToCheck, Model model) {
        Set<ProductTypeEnum> typesConverted = null;
        if (types != null) {
            if (types.isEmpty()) {
                return null;
            }
            try {
                typesConverted = types.stream().map(ProductTypeEnum::valueOf)
                        .peek(productEnum -> typeCheckboxesToCheck.add(productEnum.toString())).collect(Collectors.toSet());
                model.addAttribute("types", String.join(",", types));

                return typesConverted;
            } catch (IllegalArgumentException e) {
                throw new ProductCriteriaException("Type not valid");
            }
        }

        return typesConverted;
    }

    public Set<ProductSizeEnum> validateSize(List<String> sizes) {
        Set<ProductSizeEnum> validatedSizes = new HashSet<>();
        try {
            if (sizes.isEmpty()) {
                throw new ProductCriteriaException("Sizes is empty");
            }
            sizes.stream().map(ProductSizeEnum::valueOf).forEach(validatedSizes::add);
        } catch (IllegalArgumentException e) {
            return null;
        }

        return validatedSizes;
    }
}