package com.project.EStore.service.domain.order;

import com.project.EStore.model.service.order.OrderServiceModel;

import java.util.List;
import java.util.Map;

public interface OrderService {

    void placeOrder(Map<String, String> productsByIdAndCount, String username);

    List<OrderServiceModel> findOrdersByUsername(String username);

    void deleteOrdersByUser(String username);
}
