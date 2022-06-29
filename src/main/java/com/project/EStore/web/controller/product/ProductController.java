package com.project.EStore.web.controller.product;

import com.project.EStore.exception.ProductCriteriaException;
import com.project.EStore.exception.ProductNotFoundException;
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

import java.util.*;

@Controller
@RequestMapping("products")
public class ProductController {

    private static final int PAGE_SIZE = 8;

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final ProductValidator productValidator;

    public ProductController(ProductService productService, ModelMapper modelMapper, ProductValidator productValidator) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.productValidator = productValidator;
    }

    @GetMapping("{productCategory}")
    public String getFitnessView(
            @PathVariable String productCategory,
            @RequestParam(name = "brands", required = false) Set<String> brands,
            @RequestParam(name = "types", required = false) Set<String> types,
            @RequestParam(name = "lowestPrice", defaultValue = "0") String lowestPrice,
            @RequestParam(name = "highestPrice", defaultValue = "200") String highestPrice,
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumber,
            Model model) {

        boolean isCategoryValid = this.productValidator.isCategoryValid(productCategory);

        if (!isCategoryValid) {
            throw new ProductCriteriaException("Product category is not valid");
        }

        findAllProductsByCriteria(
                brands, types, lowestPrice, highestPrice, pageNumber, PAGE_SIZE,
                ProductCategoryEnum.valueOf(productCategory.toUpperCase()), model
        );

        return "products";
    }

    @GetMapping("{productCategory}/details/{productId}")
    public String getFitnessDetailsView(@PathVariable String productCategory, @PathVariable String productId,
                                        Model model) {

        boolean isCategoryValid = this.productValidator.isCategoryValid(productCategory);

        if (!isCategoryValid) {
            throw new ProductCriteriaException("Product category is not valid");
        }

        boolean isValid = this.productValidator.isIdValid(productId);

        if (!isValid) {
            throw new ProductCriteriaException("Id is not valid type");
        }

        ProductServiceModel productByIdAndType = this.productService
                .findProductByIdAndCategory(Integer.parseInt(productId), ProductCategoryEnum.valueOf(productCategory.toUpperCase()));

        if (productByIdAndType == null) {
            throw new ProductNotFoundException("Product not found");
        }

        ProductDetailsViewModel detailsViewModel = this.modelMapper.map(productByIdAndType, ProductDetailsViewModel.class);

        model.addAttribute("product", detailsViewModel);
        model.addAttribute("quantity", productByIdAndType.getSupply().getQuantity());

        return "productDetails";
    }

    private void findAllProductsByCriteria(
            Set<String> brands, Set<String> types,
            String lowestPrice, String highestPrice, String pageNumber, int pageSize,
            ProductCategoryEnum productCategory, Model model) {

        this.productValidator.isPriceAndPageValid(lowestPrice, highestPrice, pageNumber);

        Integer lowestPriceConverted = Integer.parseInt(lowestPrice);
        Integer highestPriceConverted = Integer.parseInt(highestPrice);
        Integer pageNumberConverted = Integer.parseInt(pageNumber);

        Set<String> brandCheckboxesToCheck = new HashSet<>();
        Set<String> brandsConverted = this.productValidator.addBrandsToCheck(brands, brandCheckboxesToCheck, model);

        Set<String> typeCheckboxesToCheck = new HashSet<>();
        Set<ProductTypeEnum> typesConverted = this.productValidator.addTypesToCheck(types, typeCheckboxesToCheck, model);

        Page<ProductServiceModel> page = this.productService.findAllByBrandAndTypeAndCategoryAndPriceBetween(
                brandsConverted, typesConverted, productCategory, lowestPriceConverted, highestPriceConverted, pageNumberConverted, pageSize
        );

        model.addAttribute("allBrands", this.productService.getAllBrandsByCategory(productCategory));
        model.addAttribute("brandCheckboxesToCheck", brandCheckboxesToCheck);
        model.addAttribute("typeCheckboxesToCheck", typeCheckboxesToCheck);
        model.addAttribute("lowerPrice", lowestPriceConverted);
        model.addAttribute("higherPrice", highestPriceConverted);
        model.addAttribute("productCategory", productCategory.toString().toLowerCase());
        model.addAttribute("allProductCards", page.map(product -> this.modelMapper.map(product, ProductCardViewModel.class)).getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageNumber", page.getPageable().getPageNumber());
    }
}