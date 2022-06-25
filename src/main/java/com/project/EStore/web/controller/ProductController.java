package com.project.EStore.web.controller;

import com.cloudinary.Cloudinary;
import com.project.EStore.exception.ProductCriteriaException;
import com.project.EStore.exception.ProductNotFoundException;
import com.project.EStore.model.binding.ProductBindingModel;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.entity.enums.ProductTypeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.service.product.ProductSizeServiceModel;
import com.project.EStore.model.service.product.ProductSupplyServiceModel;
import com.project.EStore.model.view.product.ProductCardViewModel;
import com.project.EStore.model.view.product.ProductDetailsViewModel;
import com.project.EStore.service.domain.product.ProductService;
import com.project.EStore.service.domain.product.ProductSupplyService;
import com.project.EStore.util.validation.ProductValidator;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("products")
public class ProductController {

    private static final int PAGE_SIZE = 8;

    private final ProductService productService;
    private final ProductSupplyService productSupplyService;
    private final ModelMapper modelMapper;
    private final ProductValidator productValidator;
    private final Cloudinary cloudinary;

    public ProductController(ProductService productService, ProductSupplyService productSupplyService, ModelMapper modelMapper, ProductValidator productValidator, Cloudinary cloudinary) {
        this.productService = productService;
        this.productSupplyService = productSupplyService;
        this.modelMapper = modelMapper;
        this.productValidator = productValidator;
        this.cloudinary = cloudinary;
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
        model.addAttribute("productType", productCategory.toString().toLowerCase());
        model.addAttribute("allProductCards", page.map(product -> this.modelMapper.map(product, ProductCardViewModel.class)).getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageNumber", page.getPageable().getPageNumber());
    }


    @GetMapping("add")
    public String getProductsAddView(Model model) {
        if (!model.containsAttribute("productBindingModel")) {
            model.addAttribute("productBindingModel", new ProductBindingModel());
        }
        return "addProduct";
    }

    @PostMapping("addProduct")
    public String postProductsAdd(@RequestParam(name = "productSizes", defaultValue = "") List<String> sizes,
                                  @Valid @ModelAttribute ProductBindingModel productBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        Set<ProductSizeEnum> productSizeEnums = this.productValidator.validateSize(sizes);

        if (bindingResult.hasErrors() || productSizeEnums == null) {
            redirectAttributes.addFlashAttribute("productBindingModel", productBindingModel);
            redirectAttributes.addFlashAttribute("isDataInvalid", true);

            return "redirect:/products/add";
        }

        if (!productBindingModel.getImage().getContentType().contains("image")) {
            throw new ProductCriteriaException("File is not image");
        }


        String imageUrl = null;
        try {
            File userImage = File.createTempFile("userImage", "image");
            productBindingModel.getImage().transferTo(userImage);
            Map response = this.cloudinary.uploader().upload(userImage, Map.of(
                    "public_id", String.format("%s-%s",
                            this.convertProductName(productBindingModel.getBrand()),
                            this.convertProductName(productBindingModel.getModel())),
                    "folder", String.format("EStore/%s",
                            productBindingModel.getCategory().toString().toUpperCase().charAt(0) +
                                    productBindingModel.getCategory().toString().toLowerCase().substring(1)),
                    "resource_type", "image"
            ));
            userImage.delete();
            imageUrl = (String) response.get("secure_url");
        } catch (Exception e) {
            throw new ProductCriteriaException("Unsupported image format");
        }

        ProductSupplyServiceModel productSupplyServiceModel = new ProductSupplyServiceModel();
        productSupplyServiceModel
                .setPrice(productBindingModel.getPrice().setScale(2, RoundingMode.HALF_UP))
                .setQuantity(productBindingModel.getQuantity())
                .setProduct(new ProductServiceModel()
                        .setBrand(productBindingModel.getBrand())
                        .setModel(productBindingModel.getModel())
                        .setCategory(productBindingModel.getCategory())
                        .setImageUrl(imageUrl)
                        .setType(productBindingModel.getType())
                        .setSizes(productSizeEnums
                                .stream()
                                .map(size -> new ProductSizeServiceModel().setSize(size))
                                .collect(Collectors.toSet())));


        this.productSupplyService.addSupplyWithProduct(productSupplyServiceModel);

        return "redirect:/";
    }

    private String convertProductName(String name) {
        return Arrays.stream(name.split("\\s+"))
                .map(String::toLowerCase)
                .collect(Collectors.joining("-"));
    }

    @DeleteMapping("delete/{productId}")
    public String deleteProduct(@PathVariable String productId) {

        if (!this.productValidator.isIdValid(productId)) {
            throw new ProductCriteriaException("Id not valid type");
        }

        Integer id = Integer.parseInt(productId);

        Boolean isExists = this.productService.doesExistById(id);

        if (!isExists) {
            throw new ProductNotFoundException("Product not found");
        }

        this.productService.deleteProductById(id);

        return "redirect:/products/delete/successful";
    }

    @GetMapping("delete/successful")
    public String getProductDeleteSuccessfulView() {
        return "deleteProductSuccessful";
    }
}




















