package com.project.EStore.controller;

import com.project.EStore.TestConfig;
import com.project.EStore.exception.CartCookieException;
import com.project.EStore.service.product.ProductService;
import com.project.EStore.validation.ProductCookieValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CartController.class)
@Import({TestConfig.class, ProductCookieValidator.class})
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final String url = "/cart";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

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
    void shouldReturnCartViewWhenUserIsLoggedAndNoCookiesSend() throws Exception {
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
}