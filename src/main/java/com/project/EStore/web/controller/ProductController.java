package com.project.EStore.web.controller;

import com.project.EStore.exception.ProductNotFoundException;
import com.project.EStore.exception.ProductQueryCriteriaException;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.view.product.ProductCardViewModel;
import com.project.EStore.model.view.product.ProductDetailsViewModel;
import com.project.EStore.service.domain.product.ProductService;
import com.project.EStore.util.validation.ProductValidator;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("products")
public class ProductController {

    private static final int PAGE_SIZE = 8;

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("{productCategory}")
    public String getFitnessView(
            @PathVariable String productCategory,
            @RequestParam(name = "brands", required = false) Set<String> brands,
            @RequestParam(name = "types", required = false) Set<String> types,
            @RequestParam(name = "lowestPrice", defaultValue = "0") String lowestPrice,
            @RequestParam(name = "highestPrice", defaultValue = "150") String highestPrice,
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumber,
            Model model) {

        this.isCategoryValid(productCategory);

        findAllProductsByCriteria(
                brands, types, lowestPrice, highestPrice, pageNumber, PAGE_SIZE,
                ProductCategoryEnum.valueOf(productCategory.toUpperCase()), model
        );

        return "products";
    }

    @GetMapping("{productCategory}/details/{id}")
    public String getFitnessDetailsView(@PathVariable String productCategory, @PathVariable("id") String productId, Model model) {

        this.isCategoryValid(productCategory);

        boolean isValid = ProductValidator.isIdValid(productId);

        if (!isValid) {
            throw new ProductQueryCriteriaException("Id is not valid type");
        }

        ProductServiceModel productByIdAndType = this.productService
                .findProductByIdAndType(Integer.parseInt(productId), ProductCategoryEnum.valueOf(productCategory.toUpperCase()));

        if (productByIdAndType == null) {
            throw new ProductNotFoundException("Product not found");
        }

        ProductDetailsViewModel detailsViewModel = this.modelMapper.map(productByIdAndType, ProductDetailsViewModel.class);

        model.addAttribute("product", detailsViewModel);

        return "productDetails";
    }

    private void isCategoryValid(String productCategory) {
        if (!productCategory.equals(productCategory.toLowerCase()) ||
                !ProductValidator.isCategoryValid(productCategory.toUpperCase())) {
            throw new ProductQueryCriteriaException("Product category is not valid");
        }
    }

    private void findAllProductsByCriteria(
            Set<String> brands, Set<String> types,
            String lowestPrice, String highestPrice, String pageNumber, int pageSize,
            ProductCategoryEnum productCategory, Model model) {

        ProductValidator.isPriceOrPageValid(lowestPrice, highestPrice, pageNumber);

        Integer lowestPriceConverted = Integer.parseInt(lowestPrice);
        Integer highestPriceConverted = Integer.parseInt(highestPrice);
        Integer pageNumberConverted = Integer.parseInt(pageNumber);

        Set<String> brandCheckboxesToCheck = new HashSet<>();
        Set<String> brandsConverted = ProductValidator.addBrandsToCheck(brands, brandCheckboxesToCheck, model);

        Set<String> typeCheckboxesToCheck = new HashSet<>();
        Set<ProductTypeEnum> typesConverted = ProductValidator.addTypesToCheck(types, typeCheckboxesToCheck, model);

        Page<ProductServiceModel> page = this.productService.findAllByBrandAndTypeAndCategoryAndPriceBetween(
                brandsConverted, typesConverted, productCategory, lowestPriceConverted, highestPriceConverted, pageNumberConverted, pageSize
        );

        model.addAttribute("allBrands", this.productService.getAllBrandsByCategory(productCategory));
        model.addAttribute("brandCheckboxesToCheck", brandCheckboxesToCheck);
        model.addAttribute("typeCheckboxesToCheck", typeCheckboxesToCheck);
        model.addAttribute("lowerPrice", lowestPriceConverted);
        model.addAttribute("higherPrice", highestPriceConverted);
        model.addAttribute("productType", productCategory.toString().toLowerCase());
        model.addAttribute("allProductCards", page.map(product -> this.modelMapper.map(product, ProductCardViewModel.class)).getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageNumber", page.getPageable().getPageNumber());
    }
}

