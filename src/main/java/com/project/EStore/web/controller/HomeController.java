package com.project.EStore.web.controller;

import com.project.EStore.model.entity.enums.RoleEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private static final String ADMIN_ROLE = "ROLE_" + RoleEnum.ADMIN.toString().toUpperCase();

    @GetMapping
    public String getHomeView(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();

            if (user.getAuthorities().contains(new SimpleGrantedAuthority(ADMIN_ROLE))){
                model.addAttribute("isAdmin", true);
            }
        }

        return "index";
    }
}