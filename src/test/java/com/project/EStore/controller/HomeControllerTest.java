package com.project.EStore.controller;

import com.project.EStore.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = HomeController.class)
@Import(TestConfig.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetHomeView() throws Exception {
        final String url = "/";
        final String expectedViewName = "index";

        mockMvc
                .perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }
}