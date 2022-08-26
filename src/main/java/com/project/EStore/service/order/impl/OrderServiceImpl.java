package com.project.EStore.service.order.impl;

import com.project.EStore.model.entity.order.OrderEntity;
import com.project.EStore.model.entity.user.UserEntity;
import com.project.EStore.model.service.order.OrderServiceModel;
import com.project.EStore.model.service.user.UserServiceModel;
import com.project.EStore.repository.order.OrderRepository;
import com.project.EStore.service.order.OrderDetailService;
import com.project.EStore.service.order.OrderService;
import com.project.EStore.service.product.ProductSupplyService;
import com.project.EStore.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);//TODO: Refactor with AOP

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
    @Transactional
    public void placeOrder(Map<String, String> productsByIdAndCount, String username) {
        UserServiceModel user = this.userService.findUserByUsername(username);
        OrderEntity newOrder = this.orderRepository.save(new OrderEntity().setUser(this.modelMapper.map(user, UserEntity.class)));

        this.orderDetailService.placeOrderWithDetails(productsByIdAndCount, this.modelMapper.map(newOrder, OrderServiceModel.class));
        this.productSupplyService.decrementQuantity(productsByIdAndCount);
    }

    @Override
    public List<OrderServiceModel> findOrdersByUsername(String username) {
        UserServiceModel user = this.userService.findUserByUsername(username);
        List<OrderEntity> orders = this.orderRepository.findAllByUser(this.modelMapper.map(user, UserEntity.class));

        return orders.stream().map(order -> this.modelMapper.map(order, OrderServiceModel.class)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteOrdersByUser(String username) {
        UserEntity userByUsername = this.modelMapper.map(this.userService.findUserByUsername(username), UserEntity.class);
        this.orderRepository.deleteAllByUser(userByUsername);

        LOGGER.info(String.format("All orders by user { %s } were successfully deleted", username));
    }
}