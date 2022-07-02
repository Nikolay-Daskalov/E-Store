package com.project.EStore.web.controller;

import com.project.EStore.model.service.user.RoleServiceModel;
import com.project.EStore.model.service.user.UserServiceModel;
import com.project.EStore.model.view.user.UserViewModel;
import com.project.EStore.service.domain.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public AdminController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("users")
    public String getAllUsersView(Model model, Principal principal) {
        List<UserServiceModel> users = this.userService.getAllUsers();

        model.addAttribute("users", users.stream()
                .map(user -> {
                    UserViewModel userViewModel = this.modelMapper.map(user, UserViewModel.class);
                    userViewModel.setCreatedOn(user.getCreatedOn().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                    userViewModel.setRoles(user.getRoles().stream().map(RoleServiceModel::getRole).collect(Collectors.toSet()));

                    return userViewModel;
                })
                .collect(Collectors.toList()));

        model.addAttribute("currentUser", principal.getName());

        return "allUsers";
    }
}
