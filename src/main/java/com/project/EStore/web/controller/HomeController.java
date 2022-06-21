package com.project.EStore.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    @GetMapping
    public String getIndex() {
        return "index";
    }
}