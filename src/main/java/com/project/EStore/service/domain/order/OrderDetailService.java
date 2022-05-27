package com.project.EStore.service.domain.order;

import com.project.EStore.model.service.order.OrderServiceModel;

import java.util.Map;

public interface OrderDetailService {

    void placeOrderWithDetails(Map<String, String> productsByIdAndCount, OrderServiceModel orderServiceModel);
}
