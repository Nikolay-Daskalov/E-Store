package com.project.EStore.web.controller;

import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.view.product.ProductCardViewModel;
import com.project.EStore.service.domain.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
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
        List<String> allBrands = this.productService.getAllBrands();
        model.addAttribute("allBrands", allBrands);

        List<ProductServiceModel> allProducts = this.productService.getAllProducts();
        model.addAttribute("allProductCards", allProducts.stream()
                .map(product -> this.modelMapper.map(product, ProductCardViewModel.class)).collect(Collectors.toList()));
        return "productFitness";
    }

    @GetMapping("fitness/data")
    @ResponseBody
    public ResponseEntity<Page<ProductCardViewModel>> getAllProductsByCriteria() {
//        Page<ProductServiceModel> pages = this.productService.findByPageable(1,2);
//        Page<ProductCardViewModel> map = pages.map(productServiceModel -> this.modelMapper.map(productServiceModel, ProductCardViewModel.class));
        return null;
    }
}
