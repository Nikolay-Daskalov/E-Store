package com.project.EStore.web.controller;

import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("fitness")
    public String getFitnessView(Model model) {
        Set<String> allBrands = this.productService.getAllBrands();
        model.addAttribute("allBrands", allBrands);

        List<ProductServiceModel> allProducts = this.productService.getAllProducts();
        model.addAttribute("allProductCards", allProducts.stream()
                .map(product -> this.modelMapper.map(product, ProductCardViewModel.class)).collect(Collectors.toList()));

        return "productFitness";
    }

    @GetMapping("fitness/data")
    @ResponseBody
    public ResponseEntity<Page<ProductCardViewModel>> getAllProductsByQueryParameters(
            @RequestParam(name = "brands", required = false) Set<String> brands,
            @RequestParam(name = "types", required = false) Set<ProductTypeEnum> types,
            @RequestParam(name = "lowestPrice", defaultValue = "0") BigDecimal lowestPrice,
            @RequestParam(name = "highestPrice", required = false) BigDecimal highestPrice,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber
    ) {

        Page<ProductCardViewModel> pageView = this.productService.findAllByBrandAndTypeAndPriceBetween(
                brands, types, lowestPrice, highestPrice, pageNumber, 5
        ).map(product -> this.modelMapper.map(product, ProductCardViewModel.class));

        return pageView.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pageView);
    }
}















