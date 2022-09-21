package com.project.EStore.controller;

import com.project.EStore.TestConfig;
import com.project.EStore.exception.CartCookieException;
import com.project.EStore.model.entity.enums.ProductCategoryEnum;
import com.project.EStore.model.entity.enums.ProductSizeEnum;
import com.project.EStore.model.service.product.ProductServiceModel;
import com.project.EStore.model.service.product.ProductSizeServiceModel;
import com.project.EStore.model.service.product.ProductSupplyServiceModel;
import com.project.EStore.service.product.ProductService;
import com.project.EStore.validation.ProductCookieValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockCookie;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@WebMvcTest(CartController.class)
@Import({TestConfig.class, ProductCookieValidator.class})
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final String url = "/cart";

    @Test
    @WithAnonymousUser
    void shouldRedirectToLoginViewIfUserIsAnonymous() throws Exception {
        final String headerName = "Location";
        final String headerValue = "http://localhost/users/login";

        this.mockMvc
                .perform(get(url))
                .andExpect(status().isFound())
                .andExpect(header().string(headerName, headerValue));
    }

    @Test
    @WithMockUser
    void shouldReturnEmptyCartViewWhenUserIsLoggedAndNoCookiesSend() throws Exception {
        final String viewName = "cart";

        this.mockMvc
                .perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));
    }

    @Test
    @WithMockUser
    void shouldThrowCartCookieExceptionForProductsBeingNull() throws Exception {
        final Cookie mockCookie = new MockCookie("cartProducts", "{\"products\":null}");
        final String exceptionMessage = "Cookie data is not valid";
        final String viewName = "error";

        this.mockMvc
                .perform(get(url)
                        .cookie(mockCookie))
                .andExpect(status().isBadRequest())
                .andExpect(view().name(viewName))
                .andExpect(result -> {
                    assertInstanceOf(CartCookieException.class, result.getResolvedException(),
                            "Exception is not the correct instance.");
                    assertEquals(exceptionMessage, result.getResolvedException().getMessage(), "Exception message");
                });
    }

    @Test
    @WithMockUser
    void shouldReturnEmptyCartViewWhenHasNoProducts() throws Exception {
        final Cookie mockCookie = new MockCookie("cartProducts", "{\"products\":[]}");
        final String viewName = "cart";

        this.mockMvc
                .perform(get(url)
                        .cookie(mockCookie))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));
    }

    @Test
    @WithMockUser
    void shouldReturnCartViewWithProducts() throws Exception {
        ProductServiceModel[] productServiceModels = new ProductServiceModel[]{
                new ProductServiceModel()
                        .setId(1)
                        .setBrand("Brand_1")
                        .setModel("Model_1")
                        .setImageUrl("imgUrl_1")
                        .setCategory(ProductCategoryEnum.FITNESS)
                        .setSupply(new ProductSupplyServiceModel()
                                .setQuantity(Short.valueOf("10"))
                                .setPrice(new BigDecimal("25")))
                        .setSizes(new HashSet<>()),
                new ProductServiceModel()
                        .setId(2)
                        .setBrand("Brand_2")
                        .setModel("Model_2")
                        .setImageUrl("imgUrl_2")
                        .setCategory(ProductCategoryEnum.FITNESS)
                        .setSupply(new ProductSupplyServiceModel()
                                .setQuantity(Short.valueOf("4"))
                                .setPrice(new BigDecimal("15.00")))
                        .setSizes(new HashSet<>(Set.of(
                        new ProductSizeServiceModel()
                                .setId(1)
                                .setSize(ProductSizeEnum.S),
                        new ProductSizeServiceModel()
                                .setId(2)
                                .setSize(ProductSizeEnum.M)
                )))
        };

        for (int i = 0; i < productServiceModels.length; i++) {
            when(this.productService.findProductById(i + 1)).thenReturn(productServiceModels[i]);
        }

        final String pathName = "./src/test/resources/cartProductsCookieMock.json";
        final String cookieDataJson = Files.readString(Path.of(pathName));

        final Cookie mockCookie = new MockCookie("cartProducts", cookieDataJson);

        final String viewName = "cart";
        final String[] expectedAttributes = new String[]{
                "products", "totalPrice"
        };

        this.mockMvc
                .perform(get(url)
                        .cookie(mockCookie))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName))
                .andExpect(model().attributeExists(expectedAttributes));
    }
}