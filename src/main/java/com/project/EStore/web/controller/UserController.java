package com.project.EStore.web.controller;

import com.project.EStore.model.binding.UserBindingModel;
import com.project.EStore.model.service.user.UserServiceModel;
import com.project.EStore.service.domain.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("register")
    public String getRegistrationView(Model model) {
        if (!model.containsAttribute("userBindingModel")) {
            model.addAttribute("userBindingModel", new UserBindingModel());
        }
        return "register";
    }

    @PostMapping("register")
    public String addUser(@Valid UserBindingModel user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !user.getPassword().equals(user.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userBindingModel", user);
            redirectAttributes.addFlashAttribute("bad_credentials", true);
            return "redirect:register";
        }

        boolean isUserNameUnique = this.userService.isUsernameUnique(user.getUsername());

        if (!isUserNameUnique) {
            redirectAttributes.addFlashAttribute("userNameNotUnique", true);
            return "redirect:register";
        }

        this.userService.add(modelMapper.map(user, UserServiceModel.class));

        return "redirect:login";
    }

}
