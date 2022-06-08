package com.project.EStore.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("users")
public class UserProfileController {




    @GetMapping("profile")
    public String getProfileView(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "userProfile";
    }
}
