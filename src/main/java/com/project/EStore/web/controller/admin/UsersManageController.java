package com.project.EStore.web.controller.admin;

import com.project.EStore.exception.UserNotFoundException;
import com.project.EStore.exception.UserRolesBindingException;
import com.project.EStore.model.binding.UserRoleBindingModel;
import com.project.EStore.model.entity.enums.RoleEnum;
import com.project.EStore.model.service.user.RoleServiceModel;
import com.project.EStore.model.service.user.UserServiceModel;
import com.project.EStore.model.view.user.UserViewModel;
import com.project.EStore.service.domain.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "admin/users")
public class UsersManageController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final SessionRegistry sessionRegistry;

    public UsersManageController(UserService userService, ModelMapper modelMapper, SessionRegistry sessionRegistry) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping
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


    @PatchMapping("roles")
    public String patchUserRole(@Valid @ModelAttribute UserRoleBindingModel userRoleBindingModel, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors() || userRoleBindingModel.getUsername().equalsIgnoreCase(principal.getName())) {
            throw new UserRolesBindingException("Errors on validation");
        }

        if (userRoleBindingModel.getRoles() == null) {
            userRoleBindingModel.setRoles(new HashSet<>());
        } else {
            if (userRoleBindingModel.getRoles().contains(RoleEnum.USER)) {
                throw new UserRolesBindingException("Roles should not contain USER");
            }
        }

        boolean userExists = this.userService.isUserExists(userRoleBindingModel.getUsername());

        if (!userExists) {
            throw new UserNotFoundException();
        }

        this.userService.addRolesToUser(userRoleBindingModel.getUsername(), userRoleBindingModel.getRoles());

        return "redirect:/admin/users";
    }

    @DeleteMapping
    public String deleteUser(@RequestParam String username){
        boolean userExists = this.userService.isUserExists(username);

        if (!userExists){
            throw new UserNotFoundException();
        }

        UserDetails userToLogOut = null;
        for (Object principal : this.sessionRegistry.getAllPrincipals()) {
            UserDetails currentPrincipal = (UserDetails) principal;

            if (currentPrincipal.getUsername().equals(username)){
                userToLogOut = currentPrincipal;
                break;
            }
        }

        for (SessionInformation session : this.sessionRegistry.getAllSessions(userToLogOut, false)) {
            session.expireNow();
        }

        this.userService.deleteUserByUsername(username);

        return "redirect:/admin/users";
    }
}
