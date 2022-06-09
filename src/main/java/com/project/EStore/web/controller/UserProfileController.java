package com.project.EStore.web.controller;

import com.project.EStore.model.service.order.OrderServiceModel;
import com.project.EStore.model.view.order.OrderViewModel;
import com.project.EStore.service.domain.order.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("users")
public class UserProfileController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public UserProfileController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("profile")
    public String getProfileView(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "userProfile";
    }

    @GetMapping("profile/orders")
    @ResponseBody
    public ResponseEntity<List<OrderViewModel>> getOrdersByUser(Principal principal) {
        List<OrderServiceModel> ordersByUsername = this.orderService.findOrdersByUsername(principal.getName());

        return ordersByUsername.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(ordersByUsername.stream().map(order -> this.modelMapper.map(order, OrderViewModel.class)).collect(Collectors.toList()));
    }
}
