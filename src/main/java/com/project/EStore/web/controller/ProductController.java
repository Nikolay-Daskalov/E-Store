package com.project.EStore.web.controller;

import com.project.EStore.exception.ProductCriteriaException;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.view.product.ProductCardViewModel;
import com.project.EStore.service.domain.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("fitness")
    public String getFitnessView(
            @RequestParam(name = "brands", required = false) Set<String> brands,
            @RequestParam(name = "types", required = false) Set<ProductTypeEnum> types,
            @RequestParam(name = "lowestPrice", defaultValue = "0") Integer lowestPrice,
            @RequestParam(name = "highestPrice", defaultValue = "100") Integer highestPrice,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            Model model) {

        if (lowestPrice < 0 || lowestPrice > ProductService.HIGHEST_PRICE || lowestPrice % 5 != 0){
            throw new ProductCriteriaException("Lowest price limit exceeded");
        }

        if (pageNumber < 0){
            throw new ProductCriteriaException("Page number limit exceeded");
        }

        findAllProductsByCriteria(
                brands, types, lowestPrice, highestPrice, pageNumber, PAGE_SIZE, ProductCategoryEnum.FITNESS, model
        );

        return "product";
    }

    private void findAllProductsByCriteria(
            Set<String> brands, Set<ProductTypeEnum> types,
            Integer lowestPrice, Integer highestPrice, int pageNumber, int pageSize,
            ProductCategoryEnum productCategory, Model model) {

        Set<String> brandCheckboxesToCheck = new HashSet<>();
        if (brands != null) {
            brandCheckboxesToCheck.addAll(brands);
        }

        Set<String> typeCheckboxesToCheck = new HashSet<>();
        if (types != null) {
            typeCheckboxesToCheck.addAll(types.stream().map(Enum::toString).collect(Collectors.toSet()));
        }

        Page<ProductServiceModel> page = this.productService.findAllByBrandAndTypeAndCategoryAndPriceBetween(
                brands, types, productCategory, lowestPrice, highestPrice, pageNumber, pageSize
        );
        model.addAttribute("allBrands", this.productService.getAllBrandsByCategory(ProductCategoryEnum.FITNESS));
        model.addAttribute("brandCheckboxesToCheck", brandCheckboxesToCheck);
        model.addAttribute("typeCheckboxesToCheck", typeCheckboxesToCheck);
        model.addAttribute("lowerPrice", lowestPrice);
        model.addAttribute("higherPrice", highestPrice);
        model.addAttribute("productType", productCategory.toString().toLowerCase());
        model.addAttribute("allProductCards", page.map(product -> this.modelMapper.map(product, ProductCardViewModel.class)).getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageNumber", page.getPageable().getPageNumber());
    }
}















