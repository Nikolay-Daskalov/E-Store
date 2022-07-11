package com.project.EStore.util.validation;

import com.project.EStore.exception.ProductCriteriaException;
import com.project.EStore.exception.SizeMappingException;
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

    public boolean isIdTypeValid(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isCategoryTypeValidStrict(String category) {
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
                throw new ProductCriteriaException("Page number cannot be negative");
            }
        } catch (NumberFormatException e) {
            throw new ProductCriteriaException("Price or page not valid type");
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
        if (types != null) {
            if (types.isEmpty()) {
                return null;
            }
            try {
                Set<ProductTypeEnum> typesConverted = types.stream().map(ProductTypeEnum::valueOf)
                        .peek(productEnum -> typeCheckboxesToCheck.add(productEnum.toString())).collect(Collectors.toSet());
                model.addAttribute("types", String.join(",", types));

                return typesConverted;
            } catch (IllegalArgumentException e) {
                throw new ProductCriteriaException("Type not valid");
            }
        }

        return null;
    }

    public Set<ProductSizeEnum> validateSizeStrict(List<String> sizes) {
        Set<ProductSizeEnum> validatedSizes = new HashSet<>();
        try {
            sizes.stream().map(ProductSizeEnum::valueOf).forEach(validatedSizes::add);
        } catch (IllegalArgumentException e) {
            throw new SizeMappingException();
        }

        return validatedSizes;
    }
}