package com.project.EStore.web.controller;

import com.project.EStore.service.domain.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("fitness")
    public String getFitnessView(Model model) {
        List<String> allBrands = this.productService.getAllBrands();
        model.addAttribute("allBrands", allBrands);
        return "productFitness";
    }


}
