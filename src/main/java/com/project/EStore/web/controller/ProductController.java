package com.project.EStore.web.controller;

import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.view.product.ProductCardViewModel;
import com.project.EStore.service.domain.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
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

    @GetMapping("fitness")
    public String getFitnessView(
            @RequestParam(name = "brands", required = false) Set<String> brands,
            @RequestParam(name = "types", required = false) Set<ProductTypeEnum> types,
            @RequestParam(name = "lowestPrice", defaultValue = "0") BigDecimal lowestPrice,
            @RequestParam(name = "highestPrice", required = false) BigDecimal highestPrice,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            Model model) {

        findAllProductsByCriteria(
                brands, types, lowestPrice, highestPrice, pageNumber, PAGE_SIZE, ProductCategoryEnum.FITNESS, model
        );

        return "product";
    }

    private void findAllProductsByCriteria(
            Set<String> brands, Set<ProductTypeEnum> types,
            BigDecimal lowestPrice, BigDecimal highestPrice, int pageNumber, int pageSize,
            ProductCategoryEnum productCategory, Model model) {

        Set<String> allBrands = this.productService.getAllBrandsByCategory(productCategory);
        model.addAttribute("allBrands", allBrands);

        Page<ProductCardViewModel> productCardView = this.productService.findAllByBrandAndTypeAndCategoryAndPriceBetween(
                brands, types, productCategory, lowestPrice, highestPrice, pageNumber, pageSize
        ).map(product -> this.modelMapper.map(product, ProductCardViewModel.class));

        model.addAttribute("productType", productCategory.toString().toLowerCase());
        model.addAttribute("allProductCards", productCardView.getContent());
        model.addAttribute("totalPages", productCardView.getTotalPages());
        model.addAttribute("pageNumber", productCardView.getPageable().getPageNumber());
    }
}















