package com.project.EStore.web.controller.user.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeAdminController {

    @GetMapping("admin/home")
    public String getAdminHomeView() {
        return "homeAdmin";
    }
}
