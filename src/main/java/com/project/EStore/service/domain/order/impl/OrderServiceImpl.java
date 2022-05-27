package com.project.EStore.service.domain.order.impl;

import com.project.EStore.model.entity.order.OrderEntity;
import com.project.EStore.model.entity.user.UserEntity;
import com.project.EStore.model.service.order.OrderServiceModel;
import com.project.EStore.model.service.user.UserServiceModel;
import com.project.EStore.repository.order.OrderRepository;
import com.project.EStore.service.domain.order.OrderDetailService;
import com.project.EStore.service.domain.order.OrderService;
import com.project.EStore.service.domain.product.ProductSupplyService;
import com.project.EStore.service.domain.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductSupplyService productSupplyService;
    private final OrderDetailService orderDetailService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ProductSupplyService productSupplyService, OrderDetailService orderDetailService, UserService userService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productSupplyService = productSupplyService;
        this.orderDetailService = orderDetailService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void placeOrder(Map<String, String> productsByIdAndCount, String username) {
        UserServiceModel user = this.userService.findUserByUsername(username);
        OrderEntity newOrder = this.orderRepository.save(new OrderEntity().setUser(this.modelMapper.map(user, UserEntity.class)));

        this.orderDetailService.placeOrderWithDetails(productsByIdAndCount, this.modelMapper.map(newOrder, OrderServiceModel.class));
        this.productSupplyService.buyByIds(productsByIdAndCount);
    }
}
