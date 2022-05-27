package com.project.EStore.service.domain.order;

import java.util.Map;

public interface OrderService {

    void placeOrder(Map<String, String> productsByIdAndCount, String username);
}
