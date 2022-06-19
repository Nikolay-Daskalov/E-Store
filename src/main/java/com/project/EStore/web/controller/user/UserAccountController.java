package com.project.EStore.web.controller.user;

import com.project.EStore.model.service.order.OrderServiceModel;
import com.project.EStore.model.view.order.OrderViewModel;
import com.project.EStore.service.domain.order.OrderService;
import com.project.EStore.service.domain.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("users")
public class UserAccountController {

    private final OrderService orderService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserAccountController(OrderService orderService, UserService userService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("account")
    public String getProfileView(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "userProfile";
    }

    @DeleteMapping("account")
    public String deleteUserProfile(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Principal principal) {
        this.orderService.deleteOrdersByUser(principal.getName());
        this.userService.deleteUserByUsername(principal.getName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new CookieClearingLogoutHandler("JSESSIONID").logout(httpRequest, httpResponse, authentication);
        new SecurityContextLogoutHandler().logout(httpRequest, httpResponse, authentication);

        return "redirect:/users/account/deleted";
    }

    @GetMapping("account/deleted")
    public String getAccountDeletedView() {
        return "deleteAccountSuccessful";
    }

    @GetMapping("orders")
    @ResponseBody
    public ResponseEntity<List<OrderViewModel>> getOrdersByUser(Principal principal) {
        List<OrderServiceModel> ordersByUsername = this.orderService.findOrdersByUsername(principal.getName());

        List<OrderViewModel> orders = ordersByUsername.stream().map(order -> this.modelMapper.map(order, OrderViewModel.class)).collect(Collectors.toList());
        return orders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(orders);
    }
}
