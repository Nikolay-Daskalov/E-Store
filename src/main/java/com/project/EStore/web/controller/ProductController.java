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

import java.util.HashSet;
import java.util.LinkedHashMap;
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
            @RequestParam(name = "lowestPrice", defaultValue = "0") String lowestPrice,
            @RequestParam(name = "highestPrice", defaultValue = "100") String highestPrice,
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumber,
            Model model) {

        findAllProductsByCriteria(
                brands, types, lowestPrice, highestPrice, pageNumber, PAGE_SIZE,
                ProductCategoryEnum.FITNESS, model
        );

        return "product";
    }

    private void findAllProductsByCriteria(
            Set<String> brands, Set<ProductTypeEnum> types,
            String lowestPrice, String highestPrice, String pageNumber, int pageSize,
            ProductCategoryEnum productCategory, Model model) {

        Integer lowestPriceConverted;
        Integer highestPriceConverted;
        Integer pageNumberConverted;

        try {
            lowestPriceConverted = Integer.parseInt(lowestPrice);
            highestPriceConverted = Integer.parseInt(highestPrice);
            pageNumberConverted = Integer.parseInt(pageNumber);
        } catch (NumberFormatException e) {
            throw new ProductCriteriaException("Request type not valid");
        }

        if (lowestPriceConverted < 0 || lowestPriceConverted > ProductService.HIGHEST_PRICE || lowestPriceConverted % 5 != 0) {
            throw new ProductCriteriaException("Lowest price limit exceeded");
        }

        if (pageNumberConverted < 0) {
            throw new ProductCriteriaException("Page number limit exceeded");
        }

        Set<String> brandCheckboxesToCheck = new HashSet<>();
        if (brands != null) {
            if (brands.isEmpty()) {
                brands = null;
            } else {
                brandCheckboxesToCheck.addAll(brands);
                model.addAttribute("brands", String.join(",", brands));
            }
        }

        Set<String> typeCheckboxesToCheck = new HashSet<>();
        if (types != null) {
            if (types.isEmpty()) {
                types = null;
            } else {
                typeCheckboxesToCheck.addAll(types.stream().map(Enum::toString).collect(Collectors.toSet()));
                model.addAttribute("types", types.stream().map(Enum::toString).collect(Collectors.joining(",")));
            }
        }

        Page<ProductServiceModel> page = this.productService.findAllByBrandAndTypeAndCategoryAndPriceBetween(
                brands, types, productCategory, lowestPriceConverted, highestPriceConverted, pageNumberConverted, pageSize
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


    @GetMapping("hiking")
    public String getHikingView(
            @RequestParam(name = "brands", required = false) Set<String> brands,
            @RequestParam(name = "types", required = false) Set<ProductTypeEnum> types,
            @RequestParam(name = "lowestPrice", defaultValue = "0") String lowestPrice,
            @RequestParam(name = "highestPrice", defaultValue = "100") String highestPrice,
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumber,
            Model model) {

        findAllProductsByCriteria(
                brands, types, lowestPrice, highestPrice, pageNumber, PAGE_SIZE,
                ProductCategoryEnum.HIKING, model
        );

        return "product";
    }
}















