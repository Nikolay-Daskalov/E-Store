package com.project.EStore.util.validation;

import com.project.EStore.exception.ProductQueryCriteriaException;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.util.validation.annotation.validator.NoSpecialCharactersValidator;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.LinkedHashSet;
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

    public void isPriceOrPageValid(String lowestPrice, String highestPrice, String pageNumber) {
        try {
            int lowestPriceConverted = Integer.parseInt(lowestPrice);
            int highestPriceConverted = Integer.parseInt(highestPrice);
            int pageNumberConverted = Integer.parseInt(pageNumber);

            if (lowestPriceConverted < 0 || lowestPriceConverted > HIGHEST_PRICE || lowestPriceConverted % 5 != 0) {
                throw new ProductQueryCriteriaException("Lowest price limit exceeded");
            }

            if (highestPriceConverted > HIGHEST_PRICE || highestPriceConverted < 0 || highestPriceConverted % 5 != 0) {
                throw new ProductQueryCriteriaException("Highest price limit exceeded");
            }

            if (pageNumberConverted < 0) {
                throw new ProductQueryCriteriaException("Page number limit exceeded");
            }
        } catch (NumberFormatException e) {
            throw new ProductQueryCriteriaException("Price or page not valid");
        }
    }

    public Set<String> addBrandsToCheck(Set<String> brands, Set<String> brandCheckboxesToCheck, Model model) {
        if (brands != null) {
            if (brands.isEmpty()) {
                return null;
            } else {
                boolean isValid = brands.stream().allMatch(brand -> this.noSpecialCharactersValidator.isValid(brand, null));
                if (!isValid) {
                    throw new ProductQueryCriteriaException("Brands contain special characters");
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
                throw new ProductQueryCriteriaException("Type not valid");
            }
        }

        return typesConverted;
    }
}