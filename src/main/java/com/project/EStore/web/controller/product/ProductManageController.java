package com.project.EStore.web.controller.product;

import com.cloudinary.Cloudinary;
import com.project.EStore.exception.ProductCriteriaException;
import com.project.EStore.exception.ProductNotFoundException;
import com.project.EStore.model.binding.ProductBindingModel;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.service.product.ProductSizeServiceModel;
import com.project.EStore.model.service.product.ProductSupplyServiceModel;
import com.project.EStore.model.view.product.ProductEditViewModel;
import com.project.EStore.service.domain.product.ProductService;
import com.project.EStore.service.domain.product.ProductSupplyService;
import com.project.EStore.util.validation.ProductValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("products")
public class ProductManageController {

    private final ProductService productService;
    private final ProductSupplyService productSupplyService;
    private final ProductValidator productValidator;
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;

    public ProductManageController(ProductService productService, ProductSupplyService productSupplyService, ProductValidator productValidator, Cloudinary cloudinary, ModelMapper modelMapper) {
        this.productService = productService;
        this.productSupplyService = productSupplyService;
        this.productValidator = productValidator;
        this.cloudinary = cloudinary;
        this.modelMapper = modelMapper;
    }

    @GetMapping("add")
    public String getProductsAddView(Model model) {
        if (!model.containsAttribute("productBindingModel")) {
            model.addAttribute("productBindingModel", new ProductBindingModel());
        }

        model.addAttribute("heading", "Add Products");
        model.addAttribute("url", "/products/add");
        return "addProduct";
    }

    @GetMapping("added/successfully")
    public String getAddedSuccessfullyView() {
        return "addedProductSuccessful";
    }

    @PostMapping("add")
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

        String imageUrl = this.uploadImage(productBindingModel);

        ProductSupplyServiceModel productSupplyServiceModel = this.buildProductSupplyServiceModel(productBindingModel, imageUrl, productSizeEnums);

        this.productSupplyService.addSupplyWithProduct(productSupplyServiceModel);

        return "redirect:/products/added/successfully";
    }

    private String convertProductName(String name) {
        return Arrays.stream(name.split("\\s+"))
                .map(String::toLowerCase)
                .collect(Collectors.joining("-"));
    }

    private String uploadImage(ProductBindingModel productBindingModel) {
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

        return imageUrl;
    }

    private ProductSupplyServiceModel buildProductSupplyServiceModel(ProductBindingModel productBindingModel, String imageUrl, Set<ProductSizeEnum> productSizeEnums) {
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


        return productSupplyServiceModel;
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

    @GetMapping("edit/{productId}")
    public String getEditView(@PathVariable(name = "productId") String id, Model model) {

        boolean isIdValid = this.productValidator.isIdValid(id);

        if (!isIdValid) {
            throw new ProductCriteriaException("Id is not valid type");
        }

        Integer productId = Integer.parseInt(id);

        ProductServiceModel productById = this.productService.findProductById(productId);

        if (productById == null) {
            throw new ProductNotFoundException("Product not found");
        }

        model.addAttribute("productEditViewModel", this.modelMapper.map(productById, ProductEditViewModel.class));

        return "editProduct";
    }

    @PutMapping("edit/{productId}")
    public String putProduct(@PathVariable(name = "productId") String id,
                             @RequestParam(name = "productSizes", defaultValue = "") List<String> sizes,
                             @Valid @ModelAttribute ProductBindingModel productBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("isDataInvalid", true);

            return String.format("redirect:/products/edit/%s", id);
        }

        boolean isIdValid = this.productValidator.isIdValid(id);

        if (!isIdValid) {
            redirectAttributes.addFlashAttribute("isDataInvalid", true);

            return String.format("redirect:/products/edit/%s", id);
        }

        ProductServiceModel productById = this.productService.findProductById(Integer.parseInt(id));

        if (productById == null) {
            throw new ProductNotFoundException("Product not found");
        }

        String imageUrl = null;
        if (!productBindingModel.getImage().isEmpty()) {
            if (!productBindingModel.getImage().getContentType().contains("image")) {
                throw new ProductCriteriaException("File is not image");
            }

            try {
                this.cloudinary.uploader().destroy(this.getPublicId(productById.getImageUrl()), Map.of());
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageUrl = this.uploadImage(productBindingModel);
        }

        Set<ProductSizeEnum> productSizeEnums = this.productValidator.validateSize(sizes);

        this.productSupplyService.replaceSupplyWithProduct(this.buildProductSupplyServiceModel(productBindingModel, imageUrl, productSizeEnums).setId(Integer.parseInt(id)));

        return String.format("redirect:/products/edit/%s", id);
    }

    private String getPublicId(String imageUrl) {
        String publicIdWithExtension = imageUrl.split("/EStore")[1];
        String publicId = publicIdWithExtension.split("\\.")[0];

        return "EStore" + publicId;
    }
}
